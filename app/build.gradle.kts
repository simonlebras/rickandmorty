plugins {
  alias(libs.plugins.rickandmorty.aboutlibraries)
  alias(libs.plugins.rickandmorty.android.application)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.firebase.crashlytics)
  alias(libs.plugins.rickandmorty.firebase.perf)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.androidx.baselineprofile)
}

android {
  namespace = "app.rickandmorty"

  defaultConfig {
    applicationId = "app.rickandmorty"
    versionCode = 1
    versionName = "1.0"
  }

  androidResources { generateLocaleConfig = true }

  val useReleaseKeystore = layout.settingsDirectory.file("keystore/release.jks").asFile.exists()

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
    debug {
      signingConfig = signingConfigs["debug"]
      applicationIdSuffix = ".debug"
      versionNameSuffix = "-debug"
      isPseudoLocalesEnabled = true
    }

    release {
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
  implementation(project(":core:filesystem"))
  implementation(project(":core:graphql-client"))
  implementation(project(":core:json"))
  implementation(project(":core:ktor"))
  implementation(project(":core:l10n"))
  implementation(project(":core:process-lifecycle"))
  implementation(project(":core:resource-state"))
  implementation(project(":core:resources-app"))
  implementation(project(":core:root-content"))
  implementation(project(":core:startup"))
  implementation(project(":core:ui"))

  implementation(project(":data:character-impl"))
  implementation(project(":data:database-impl"))
  implementation(project(":data:episode-impl"))
  implementation(project(":data:license-impl"))
  implementation(project(":data:locale-impl"))
  implementation(project(":data:location-impl"))
  implementation(project(":data:theme-impl"))

  implementation(project(":ui:character-impl"))
  implementation(project(":ui:episode-impl"))
  implementation(project(":ui:location-impl"))
  implementation(project(":ui:settings-impl"))

  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.browser)
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.androidx.lifecycle.runtime.compose)

  implementation(libs.coil)

  implementation(libs.jetbrains.compose.material3.adaptive.navigation3)
  implementation(libs.jetbrains.compose.material3.adaptivenavigationsuite)
  implementation(libs.jetbrains.compose.resources)
  implementation(libs.jetbrains.navigation3.ui)

  implementation(libs.kotlinx.collectionsimmutable)

  implementation(libs.metrox.android)
  implementation(libs.metrox.viewmodel.compose)

  releaseImplementation(project(":core:compose-diagnostic"))
  releaseImplementation(project(":core:crashlytics"))
  releaseImplementation(project(":core:logger-crashlytics"))

  debugImplementation(project(":core:coil-logger"))
  debugImplementation(project(":core:logger-debug"))
  debugImplementation(project(":core:strict-mode"))

  debugImplementation(project(":data:debug-impl"))

  debugImplementation(project(":ui:debug-drawer"))

  compileOnly(project(":core:metro-common"))

  runtimeOnly(libs.androidx.compose.runtime.tracing)
  runtimeOnly(libs.androidx.profileinstaller)

  runtimeOnly(libs.leakcanary.plumber)

  debugRuntimeOnly(libs.leakcanary)

  baselineProfile(project(":baseline-profile"))
}

dependencyAnalysis {
  issues {
    onUnusedDependencies {
      exclude(
        libs.jetbrains.compose.material3.adaptive.navigation3,
        libs.jetbrains.compose.material3.adaptivenavigationsuite,
        libs.jetbrains.navigation3.ui,
      )
      exclude(
        ":core:compose-diagnostic",
        ":core:crashlytics",
        ":core:logger-crashlytics",
      )
    }
  }
}
