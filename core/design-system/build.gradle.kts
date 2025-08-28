plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.designsystem" }

  sourceSets {
    androidMain { dependencies { implementation(libs.androidx.navigation3.ui) } }

    commonMain {
      dependencies {
        api(libs.jetbrains.compose.material3)

        implementation(compose.components.resources)

        implementation(libs.androidx.annotation)

        implementation(libs.coil.compose)

        implementation(libs.compose.placeholder.material3)
      }
    }
  }
}

compose.resources { packageOfResClass = "app.rickandmorty.core.designsystem.resources" }
