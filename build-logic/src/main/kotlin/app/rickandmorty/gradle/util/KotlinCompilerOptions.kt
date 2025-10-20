package app.rickandmorty.gradle.util

import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinNativeCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun KotlinProjectExtension.configureKotlinCompilerOptions() {
  configureCompilerOptions {
    freeCompilerArgs.addAll(
      "-Xannotation-default-target=param-property",
      "-Xcontext-sensitive-resolution",
    )
    allWarningsAsErrors.set(this !is KotlinNativeCompilerOptions)
    progressiveMode.set(true)
  }
}

private fun KotlinProjectExtension.configureCompilerOptions(
  configure: KotlinCommonCompilerOptions.() -> Unit
) {
  when (this) {
    is KotlinAndroidProjectExtension,
    is KotlinJvmProjectExtension -> compilerOptions.configure()
    is KotlinMultiplatformExtension -> {
      targets.configureEach {
        compilations.configureEach { compileTaskProvider.configure { compilerOptions.configure() } }
      }
    }

    else -> error("Unknown Kotlin extension: $this")
  }
}
