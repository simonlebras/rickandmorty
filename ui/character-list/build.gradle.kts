plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
}

android {
  namespace = "app.rickandmorty.ui.character.list"

  androidResources.enable = true
}

dependencies {
  api(project(":data:character-api"))

  implementation(project(":core:design-system"))
  implementation(project(":core:l10n"))
  implementation(project(":core:ui"))
  implementation(project(":core:ui-tooling-preview"))

  implementation(libs.androidx.paging.compose)

  implementation(libs.coil.compose.core)
}

dependencyAnalysis { issues { onAny { exclude(":core:ui-tooling-preview") } } }
