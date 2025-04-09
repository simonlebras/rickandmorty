package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureGradleManagedDevices
import app.rickandmorty.gradle.util.configureLint
import app.rickandmorty.gradle.util.coreLibraryDesugaring
import app.rickandmorty.gradle.util.lintChecks
import app.rickandmorty.gradle.util.withPlugin
import com.android.build.api.dsl.androidLibrary
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class AndroidMultiplatformLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      pluginManager.apply(libs.plugins.android.multiplatformlibrary)

      pluginManager.withPlugin(libs.plugins.kotlin.multiplatform) {
        configure<KotlinMultiplatformExtension> {
          androidLibrary {
            compileSdk = libs.versions.android.sdk.compile.get().toInt()
            minSdk = libs.versions.android.sdk.min.get().toInt()

            lint {
              targetSdk = project.libs.versions.android.sdk.target.get().toInt()
              configureLint()
            }

            withDeviceTest {
              animationsDisabled = true

              configureGradleManagedDevices()
            }

            dependencyVariantSelection { buildTypes = listOf("release") }

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
