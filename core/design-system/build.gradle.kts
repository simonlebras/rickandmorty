plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  android { namespace = "app.rickandmorty.core.designsystem" }

  dependencies {
    api(libs.coil.compose)

    api(libs.jetbrains.compose.material3)

    implementation(project(":core:l10n"))

    implementation(libs.compose.placeholder.material3)

    implementation(libs.jetbrains.compose.resources)
    implementation(libs.jetbrains.navigation3.ui)
  }
}

compose.resources { packageOfResClass = "app.rickandmorty.core.designsystem.resources" }
