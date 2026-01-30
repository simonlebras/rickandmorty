package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureCompilerOptions
import app.rickandmorty.gradle.util.configureJvmCompatibility
import app.rickandmorty.gradle.util.isAndroidTestEnabled
import app.rickandmorty.gradle.util.kotlinAndroid
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AndroidLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.android.library)

      kotlinAndroid {
        configureCompilerOptions()

        explicitApi()
      }

      configureJvmCompatibility()

      configure<LibraryExtension> {
        configureAndroid()

        buildTypes { release { isDefault = true } }
        testBuildType = "release"
      }

      configure<LibraryAndroidComponentsExtension> {
        beforeVariants { builder ->
          if (builder.buildType == "debug") {
            builder.enable = false
          }

          builder.androidTest.enable = isAndroidTestEnabled
        }
      }
    }
}
