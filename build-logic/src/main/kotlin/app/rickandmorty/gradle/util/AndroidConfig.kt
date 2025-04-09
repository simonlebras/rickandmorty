package app.rickandmorty.gradle.util

import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid(commonExtension: AndroidCommonExtension) {
  with(commonExtension) {
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    defaultConfig.apply { minSdk = libs.versions.android.sdk.min.get().toInt() }

    lint.configureLint()

    testOptions {
      animationsDisabled = true

      configureGradleManagedDevices()
    }

    compileOptions {
      val javaTarget = libs.versions.java.target.get()
      sourceCompatibility(javaTarget)
      targetCompatibility(javaTarget)

      isCoreLibraryDesugaringEnabled = true
    }
  }

  dependencies {
    coreLibraryDesugaring(libs.android.tools.desugarjdklibs)

    lintChecks(libs.android.tools.security.lints)
  }
}
