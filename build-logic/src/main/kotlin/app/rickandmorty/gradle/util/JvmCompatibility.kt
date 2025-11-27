package app.rickandmorty.gradle.util

import org.gradle.api.Project
import tapmoc.configureJavaCompatibility

private const val JVM_TARGET = 17

internal fun Project.configureJvmCompatibility() {
  configureJavaCompatibility(JVM_TARGET)
}
