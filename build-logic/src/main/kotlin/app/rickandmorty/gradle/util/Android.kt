package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

context(project: Project)
internal fun CommonExtension.configureAndroid() {
  val libs = project.the<LibrariesForLibs>()

  defaultConfig.apply { testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" }

  lint.configureLint()

  testOptions.animationsDisabled = true

  compileOptions.isCoreLibraryDesugaringEnabled = true

  project.dependencies {
    coreLibraryDesugaring(libs.android.tools.desugarjdklibs)

    lintChecks(libs.android.tools.security.lints)
  }
}

internal val Project.isAndroidTestEnabled
  get() = providers.getBooleanProperty(name = "ram.enableAndroidTest", default = false)
