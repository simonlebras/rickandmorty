package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.assign
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureGradleManagedDevices
import app.rickandmorty.gradle.util.configureLint
import app.rickandmorty.gradle.util.coreLibraryDesugaring
import app.rickandmorty.gradle.util.lintChecks
import app.rickandmorty.gradle.util.withPlugin
import com.android.build.api.dsl.androidLibrary
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class AndroidMultiplatformLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.android.multiplatformlibrary, libs.plugins.android.lint)

      pluginManager.withPlugin(libs.plugins.kotlin.multiplatform) {
        configure<KotlinMultiplatformExtension> {
          androidLibrary {
            compileSdk = libs.versions.android.sdk.compile.get().toInt()
            minSdk = libs.versions.android.sdk.min.get().toInt()

            lint {
              targetSdk = libs.versions.android.sdk.target.get().toInt()
              configureLint()
            }

            withDeviceTest {
              animationsDisabled = true

              managedDevices { configureGradleManagedDevices() }
            }

            localDependencySelection { selectBuildTypeFrom = listOf("release") }

            compilations.configureEach {
              @Suppress("DEPRECATION")
              compilerOptions.configure {
                jvmTarget = JvmTarget.fromTarget(libs.versions.java.target.get())
              }
            }

            enableCoreLibraryDesugaring = true
          }

          sourceSets.androidMain {
            dependencies.apply {
              coreLibraryDesugaring(libs.android.tools.desugarjdklibs)
              lintChecks(libs.android.tools.security.lints)
            }
          }
        }
      }
    }
}
