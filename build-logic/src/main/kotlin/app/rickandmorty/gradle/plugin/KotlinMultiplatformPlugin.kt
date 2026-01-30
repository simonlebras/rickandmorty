package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureCompilerOptions
import app.rickandmorty.gradle.util.configureJvmCompatibility
import app.rickandmorty.gradle.util.kotlinMultiplatform
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class KotlinMultiplatformPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.kotlin.multiplatform)

      configureJvmCompatibility()

      kotlinMultiplatform {
        applyDefaultHierarchyTemplate()

        iosArm64()
        iosSimulatorArm64()

        jvm()

        configureCompilerOptions()

        explicitApi()
      }
    }
}
