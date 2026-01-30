package app.rickandmorty.gradle.util

import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinNativeCompilerOptions

internal fun KotlinAndroidProjectExtension.configureCompilerOptions() {
  compilerOptions { configureJvmTargetCompilerOptions() }
}

internal fun KotlinJvmProjectExtension.configureCompilerOptions() {
  compilerOptions { configureJvmTargetCompilerOptions() }
}

internal fun KotlinMultiplatformExtension.configureCompilerOptions() {
  targets.configureEach {
    compilations.configureEach {
      compileTaskProvider.configure {
        compilerOptions {
          configureCommonCompilerOptions()
          freeCompilerArgs.add("-Xexpect-actual-classes")
          allWarningsAsErrors.set(this !is KotlinNativeCompilerOptions)
        }
      }
    }
  }
}

private fun KotlinCommonCompilerOptions.configureCommonCompilerOptions() {
  freeCompilerArgs.addAll(
    "-Xannotation-default-target=param-property",
    "-Xcontext-sensitive-resolution",
  )
  progressiveMode.set(true)
}

private fun KotlinCommonCompilerOptions.configureJvmTargetCompilerOptions() {
  configureCommonCompilerOptions()
  freeCompilerArgs.add("-jvm-default=no-compatibility")
  allWarningsAsErrors.set(true)
}
