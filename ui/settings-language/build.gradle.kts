plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
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
