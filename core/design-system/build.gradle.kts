plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  android {
    namespace = "app.rickandmorty.core.designsystem"

    androidResources.enable = true
  }

  dependencies {
    api(libs.coil.compose)

    api(libs.jetbrains.compose.material3)
    api(libs.jetbrains.compose.resources)
    api(libs.jetbrains.compose.ui.tooling.preview)

    implementation(project(":core:l10n"))

    implementation(libs.compose.placeholder.material3)

    implementation(libs.haze.materials)

    implementation(libs.jetbrains.navigation3.ui)
  }
}

compose.resources { packageOfResClass = "app.rickandmorty.core.designsystem.resources" }

dependencyAnalysis {
  issues {
    onUnusedDependencies {
      exclude(libs.jetbrains.compose.material3)
      exclude(libs.jetbrains.compose.ui.tooling.preview, libs.jetbrains.navigation3.ui)
    }
  }
}
