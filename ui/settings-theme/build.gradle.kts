plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.android)
}

android { namespace = "app.rickandmorty.ui.settings.theme" }

dependencies {
  api(project(":core:resource-state"))

  api(project(":data:theme-api"))

  implementation(project(":core:coroutines"))
  implementation(project(":core:l10n"))
  implementation(project(":core:ui"))

  implementation(project(":ui:settings-common"))

  implementation(compose.material3)

  implementation(libs.kotlinx.collectionsimmutable)
}

dependencyAnalysis { issues { onUnusedDependencies { exclude(compose.dependencies.material3) } } }
