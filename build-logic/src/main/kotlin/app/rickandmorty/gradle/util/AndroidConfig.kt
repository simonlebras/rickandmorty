package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

context(commonExtension: AndroidCommonExtension)
internal fun Project.configureAndroid() {
  val libs = the<LibrariesForLibs>()

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
