package app.rickandmorty.gradle.util

import compat.patrouille.configureJavaCompatibility
import org.gradle.api.Project

private const val JVM_TARGET = 17

internal fun Project.configureJvmCompatibility() {
  configureJavaCompatibility(JVM_TARGET)
}
