plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android { namespace = "app.rickandmorty.ui.settings.common" }

dependencies {
  api(project(":data:theme-api"))

  implementation(project(":core:l10n"))

  implementation(compose.material3)
}

dependencyAnalysis { issues { onUnusedDependencies { exclude(compose.dependencies.material3) } } }
