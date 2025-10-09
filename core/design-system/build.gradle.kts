import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.designsystem" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(libs.jetbrains.compose.material3)

    implementation(libs.androidx.annotation)

    implementation(libs.coil.compose)

    implementation(libs.compose.placeholder.material3)

    implementation(libs.jetbrains.compose.resources)
  }

  sourceSets { androidMain { dependencies { implementation(libs.androidx.navigation3.ui) } } }
}

compose.resources { packageOfResClass = "app.rickandmorty.core.designsystem.resources" }
