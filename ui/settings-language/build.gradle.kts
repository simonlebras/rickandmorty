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
  api(project(":core:resource-state"))

  api(project(":data:locale"))

  implementation(project(":core:coroutines"))
  implementation(project(":core:design-system"))
  implementation(project(":core:l10n"))
  implementation(project(":core:ui"))

  implementation(project(":ui:settings-common"))

  implementation(libs.kotlinx.collectionsimmutable)
}
