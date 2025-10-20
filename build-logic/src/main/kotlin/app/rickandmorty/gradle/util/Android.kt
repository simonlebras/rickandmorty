package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

internal object AndroidSdkVersions {
  const val COMPILE_SDK = 36
  const val MIN_SDK = 28
  const val TARGET_SDK = 36
}

context(project: Project)
internal fun CommonExtension.configureAndroid() {
  val libs = project.the<LibrariesForLibs>()

  compileSdk = AndroidSdkVersions.COMPILE_SDK

  defaultConfig.apply {
    minSdk = AndroidSdkVersions.MIN_SDK

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  lint.configureLint()

  testOptions { animationsDisabled = true }

  compileOptions { isCoreLibraryDesugaringEnabled = true }

  project.dependencies {
    coreLibraryDesugaring(libs.android.tools.desugarjdklibs)

    lintChecks(libs.android.tools.security.lints)
  }
}

internal val Project.isAndroidTestEnabled
  get() = providers.getBooleanProperty(name = "ram.enableAndroidTest", default = false)
