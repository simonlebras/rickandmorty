plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
}

android { namespace = "app.rickandmorty.ui.location.list" }

dependencies {
  api(project(":data:location-api"))

  implementation(project(":core:design-system"))
  implementation(project(":core:l10n"))
  implementation(project(":core:ui"))

  implementation(libs.androidx.paging.compose)
}
