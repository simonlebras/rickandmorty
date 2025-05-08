plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android { namespace = "app.rickandmorty.ui.settings.theme" }

dependencies {
  api(projects.core.resourceState)

  api(projects.data.theme)

  implementation(compose.material3)

  implementation(libs.androidx.activity.compose)

  implementation(libs.kotlinx.collectionsimmutable)

  implementation(projects.core.coroutines)
  implementation(projects.core.l10n)

  implementation(projects.ui.settingsCommon)
}

dependencyAnalysis { issues { onUnusedDependencies { exclude(compose.dependencies.material3) } } }
