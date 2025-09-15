package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.assign
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.dsl.withType
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureKotlin() {
  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.freeCompilerArgs.addAll(
      "-Xannotation-default-target=param-property",
      "-Xcontext-sensitive-resolution",
      "-Xmulti-dollar-interpolation",
    )
  }

  val libs = the<LibrariesForLibs>()
  val javaTarget = libs.versions.java.target.get()

  tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
      jvmTarget = JvmTarget.fromTarget(javaTarget)

      allWarningsAsErrors = true
    }
  }

  tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = javaTarget
    targetCompatibility = javaTarget
  }
}
