plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android { namespace = "app.rickandmorty.ui.settings.language" }

dependencies {
  api(projects.core.resourceState)

  api(projects.data.locale)

  implementation(libs.androidx.activity.compose)

  implementation(libs.kotlinx.collectionsimmutable)

  implementation(projects.core.coroutines)
  implementation(projects.core.designSystem)
  implementation(projects.core.l10n)

  implementation(projects.ui.settingsCommon)
}
