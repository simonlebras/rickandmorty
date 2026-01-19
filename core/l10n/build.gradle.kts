plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  dependencies {
    api(libs.jetbrains.compose.resources)

    implementation(libs.jetbrains.compose.runtime)
  }
}

compose.resources {
  packageOfResClass = "app.rickandmorty.core.l10n.resources"
  publicResClass = true
}
