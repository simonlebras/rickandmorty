package app.rickandmorty.gradle.util

import com.android.build.api.dsl.Lint

internal fun Lint.configureLint() {
  checkReleaseBuilds = false
  warningsAsErrors = true
  disable += setOf("AndroidGradlePluginVersion", "GradleDependency", "OldTargetApi")
}
