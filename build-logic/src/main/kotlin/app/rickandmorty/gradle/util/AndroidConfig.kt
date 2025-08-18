package app.rickandmorty.gradle.util

import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

context(commonExtension: AndroidCommonExtension)
internal fun Project.configureAndroid() {
  with(commonExtension) {
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    defaultConfig.apply { minSdk = libs.versions.android.sdk.min.get().toInt() }

    lint.configureLint()

    testOptions {
      animationsDisabled = true

      managedDevices { configureGradleManagedDevices() }
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
