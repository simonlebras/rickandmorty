plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
}

android { namespace = "app.rickandmorty.ui.settings.common" }

dependencies {
  api(project(":data:theme-api"))

  implementation(project(":core:l10n"))

  implementation(libs.jetbrains.compose.material3)
}

dependencyAnalysis { issues { onUnusedDependencies { exclude(libs.jetbrains.compose.material3) } } }
