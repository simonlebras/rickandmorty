import com.google.firebase.perf.plugin.FirebasePerfExtension

plugins {
  alias(libs.plugins.rickandmorty.android.application)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.modulegraphassert)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.androidx.baselineprofile)
  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.metro)
  alias(libs.plugins.sortdependencies)
}

val useFirebase = file("google-services.json").exists()

if (useFirebase) {
  apply(plugin = "app.rickandmorty.firebase-crashlytics")
  apply(plugin = "app.rickandmorty.firebase-perf")
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
        configure<FirebasePerfExtension> { setInstrumentationEnabled(false) }
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
  implementation(compose.material3AdaptiveNavigationSuite) {
    exclude(group = "org.jetbrains.androidx.window")
  }

  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core.splashscreen)

  implementation(libs.coil)

  implementation(projects.core.base)
  implementation(projects.core.coil)
  implementation(projects.core.coroutines)
  implementation(projects.core.designSystem)
  implementation(projects.core.ktor)
  implementation(projects.core.metro)
  implementation(projects.core.processLifecycle)
  implementation(projects.core.resourceState)
  implementation(projects.core.resourcesApp)
  implementation(projects.core.startup)
  implementation(projects.core.ui)

  implementation(projects.data.filesystem)
  implementation(projects.data.themeImpl)

  releaseImplementation(projects.core.crashlytics)
  releaseImplementation(projects.core.loggerCrashlytics)

  debugImplementation(projects.core.coilLogger)
  debugImplementation(projects.core.loggerDebug)
  debugImplementation(projects.core.strictMode)

  runtimeOnly(libs.androidx.compose.runtime.tracing)
  runtimeOnly(libs.androidx.profileinstaller)

  runtimeOnly(libs.leakcanary.plumber)

  debugRuntimeOnly(libs.leakcanary)

  baselineProfile(projects.baselineProfile)
}

dependencyAnalysis {
  issues { onUnusedDependencies { exclude(compose.dependencies.material3AdaptiveNavigationSuite) } }
}
