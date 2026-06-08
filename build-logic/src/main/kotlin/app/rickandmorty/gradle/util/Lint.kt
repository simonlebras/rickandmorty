package app.rickandmorty.gradle.util

import com.android.build.api.dsl.Lint

internal fun Lint.configureLint() {
  checkDependencies = false
  checkReleaseBuilds = false
  warningsAsErrors = true
  disable.addAll(
    ["AndroidGradlePluginVersion", "GradleDependency", "Instantiatable", "OldTargetApi"]
  )
}
