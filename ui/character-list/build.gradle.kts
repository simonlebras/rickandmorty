plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android {
  namespace = "app.rickandmorty.ui.character.list"

  androidResources.enable = true
}

dependencies {
  api(projects.data.character)

  implementation(libs.androidx.activity.compose)

  implementation(libs.coil.compose.core)

  implementation(projects.core.designSystem)
  implementation(projects.core.l10n)
  implementation(projects.core.ui)
  implementation(projects.core.uiToolingPreview)

  implementation(projects.thirdparty.androidxPagingCompose)
}

dependencyAnalysis { issues { onAny { exclude(":core:ui-tooling-preview") } } }
