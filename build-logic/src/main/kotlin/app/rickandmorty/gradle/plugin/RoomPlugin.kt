package app.rickandmorty.gradle.plugin

import androidx.room.gradle.RoomExtension
import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.withPlugin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class RoomPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.androidx.room, libs.plugins.ksp)

      configure<RoomExtension> { schemaDirectory("$projectDir/schemas") }

      pluginManager.withPlugin(libs.plugins.kotlin.multiplatform) {
        configure<KotlinMultiplatformExtension> {
          sourceSets.commonMain.dependencies {
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
          }
        }
      }

      dependencies {
        add("kspAndroid", libs.androidx.room.compiler)
        add("kspIosArm64", libs.androidx.room.compiler)
        add("kspIosSimulatorArm64", libs.androidx.room.compiler)
        add("kspJvm", libs.androidx.room.compiler)
      }
    }
}
