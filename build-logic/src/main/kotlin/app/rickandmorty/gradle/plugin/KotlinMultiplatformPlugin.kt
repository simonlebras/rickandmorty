package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureKotlin
import app.rickandmorty.gradle.util.withPlugin
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

public class KotlinMultiplatformPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      pluginManager.apply(libs.plugins.kotlin.multiplatform)

      configureKotlin()

      configure<KotlinMultiplatformExtension> {
        applyDefaultHierarchyTemplate()

        pluginManager.withPlugin(libs.plugins.android.library) { androidTarget() }

        jvm()

        iosArm64()
        iosSimulatorArm64()

        targets.withType<KotlinJvmTarget> {
          compilations.configureEach {
            compileTaskProvider.configure {
              compilerOptions {
                val javaTarget = libs.versions.java.target.get()
                freeCompilerArgs.addAll("-Xjdk-release=$javaTarget")
              }
            }
          }
        }

        targets.configureEach {
          compilations.configureEach {
            compileTaskProvider.configure {
              compilerOptions { freeCompilerArgs.addAll("-Xexpect-actual-classes") }
            }
          }
        }

        explicitApi()
      }
    }
}
