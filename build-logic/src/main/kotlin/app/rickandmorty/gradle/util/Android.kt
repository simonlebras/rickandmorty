package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

context(project: Project)
internal fun CommonExtension.configureAndroid() {
  val libs = project.the<LibrariesForLibs>()

  compileSdk = libs.versions.android.sdk.compile.get().toInt()

  defaultConfig.apply {
    minSdk = libs.versions.android.sdk.min.get().toInt()

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

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

  project.dependencies {
    coreLibraryDesugaring(libs.android.tools.desugarjdklibs)

    lintChecks(libs.android.tools.security.lints)
  }
}
