package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.dsl.withType
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinNativeCompilerOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import tapmoc.configureJavaCompatibility

internal fun Project.configureKotlin() {
  val libs = the<LibrariesForLibs>()

  configureJavaCompatibility(17)

  plugins.withType<KotlinBasePlugin>().configureEach {
    tasks.withType<KotlinCompilationTask<*>>().configureEach {
      compilerOptions {
        progressiveMode.set(true)
        allWarningsAsErrors.convention(true)
        freeCompilerArgs.addAll("-Xcollection-literals", "-Xcontext-sensitive-resolution")

        when (this) {
          is KotlinJvmCompilerOptions -> freeCompilerArgs.add("-jvm-default=no-compatibility")
          is KotlinNativeCompilerOptions -> allWarningsAsErrors.convention(false)
        }
      }
    }
  }

  pluginManager.withPlugin(libs.plugins.kotlin.multiplatform) {
    tasks.withType<KotlinCompilationTask<*>>().configureEach {
      compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
    }
  }
}
