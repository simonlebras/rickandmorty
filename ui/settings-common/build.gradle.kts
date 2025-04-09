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
  api(projects.data.model)

  implementation(compose.material3)

  implementation(projects.core.l10n)
}

dependencyAnalysis { issues { onUnusedDependencies { exclude(compose.dependencies.material3) } } }
