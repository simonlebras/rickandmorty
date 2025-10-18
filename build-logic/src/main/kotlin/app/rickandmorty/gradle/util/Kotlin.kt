package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.assign
import app.rickandmorty.gradle.dsl.withType
import compat.patrouille.configureJavaCompatibility
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin() {
  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      freeCompilerArgs.addAll(
        "-Xannotation-default-target=param-property",
        "-Xcontext-sensitive-resolution",
      )

      allWarningsAsErrors = true
    }
  }

  configureJavaCompatibility(17)
}
