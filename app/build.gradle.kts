// TODO Firebase does not work with AGP 9
// https://github.com/firebase/firebase-android-sdk/issues/7293
// import com.google.firebase.perf.plugin.FirebasePerfExtension

plugins {
  alias(libs.plugins.rickandmorty.android.application)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)

  alias(libs.plugins.androidx.baselineprofile)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.metro)
}

val useFirebase = file("google-services.json").exists()

if (useFirebase) {
  apply(plugin = "app.rickandmorty.firebase-crashlytics")
  // apply(plugin = "app.rickandmorty.firebase-perf")
}

val useReleaseKeystore = layout.settingsDirectory.file("keystore/release.jks").asFile.exists()

android {
  namespace = "app.rickandmorty"

  defaultConfig {
    applicationId = "app.rickandmorty"
    versionCode = 1
    versionName = "1.0"
  }

  androidResources { generateLocaleConfig = true }

  signingConfigs {
    getByName("debug") {
      storeFile = layout.settingsDirectory.file("keystore/debug.jks").asFile
      storePassword = "rickandmorty"
      keyAlias = "debug"
      keyPassword = "rickandmorty"
    }

    create("release") {
      if (useReleaseKeystore) {
        storeFile = layout.settingsDirectory.file("keystore/release.jks").asFile
        storePassword = providers.gradleProperty("RAM_RELEASE_KEYSTORE_PWD").getOrElse("")
        keyAlias = "release"
        keyPassword = providers.gradleProperty("RAM_RELEASE_KEY_PWD").getOrElse("")
      }
    }
  }

  buildTypes {
    val debug by getting {
      signingConfig = signingConfigs["debug"]
      applicationIdSuffix = ".debug"
      versionNameSuffix = "-debug"
      isPseudoLocalesEnabled = true

      if (useFirebase) {
        // configure<FirebasePerfExtension> { setInstrumentationEnabled(false) }
      }
    }

    val release by getting {
      signingConfig = signingConfigs[if (useReleaseKeystore) "release" else "debug"]
      isShrinkResources = true
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

baselineProfile {
  baselineProfileRulesRewrite = true
  dexLayoutOptimization = true
}

dependencies {
  implementation(project(":core:base"))
  implementation(project(":core:coil"))
  implementation(project(":core:coroutines"))
  implementation(project(":core:design-system"))
  implementation(project(":core:ktor"))
  implementation(project(":core:metro"))
  implementation(project(":core:process-lifecycle"))
  implementation(project(":core:resource-state"))
  implementation(project(":core:resources-app"))
  implementation(project(":core:startup"))
  implementation(project(":core:ui"))

  implementation(project(":data:filesystem"))
  implementation(project(":data:graphql-client"))
  implementation(project(":data:theme-impl"))

  implementation(compose.material3AdaptiveNavigationSuite)

  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core.splashscreen)

  implementation(libs.coil)

  releaseImplementation(project(":core:crashlytics"))
  releaseImplementation(project(":core:logger-crashlytics"))

  debugImplementation(project(":core:coil-logger"))
  debugImplementation(project(":core:compose-debug"))
  debugImplementation(project(":core:logger-debug"))
  debugImplementation(project(":core:strict-mode"))

  runtimeOnly(libs.androidx.compose.runtime.tracing)
  runtimeOnly(libs.androidx.profileinstaller)

  runtimeOnly(libs.leakcanary.plumber)

  debugRuntimeOnly(libs.leakcanary)

  androidTestRuntimeOnly(libs.androidx.test.runner)

  baselineProfile(project(":baseline-profile"))
}

dependencyAnalysis {
  issues { onUnusedDependencies { exclude(compose.dependencies.material3AdaptiveNavigationSuite) } }
}
