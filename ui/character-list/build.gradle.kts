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
  api(project(":data:character"))

  implementation(project(":core:design-system"))
  implementation(project(":core:l10n"))
  implementation(project(":core:ui"))
  implementation(project(":core:ui-tooling-preview"))

  implementation(project(":thirdparty:androidx-paging-compose"))

  implementation(libs.androidx.activity.compose)

  implementation(libs.coil.compose.core)
}

dependencyAnalysis { issues { onAny { exclude(":core:ui-tooling-preview") } } }
