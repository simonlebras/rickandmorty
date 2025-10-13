package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureKotlin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class KotlinMultiplatformPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.kotlin.multiplatform)

      configureKotlin()

      configure<KotlinMultiplatformExtension> {
        applyDefaultHierarchyTemplate()

        jvm()

        iosArm64()
        iosSimulatorArm64()

        compilerOptions { freeCompilerArgs.addAll("-Xexpect-actual-classes") }

        explicitApi()
      }
    }
}
