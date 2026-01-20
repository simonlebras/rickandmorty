plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  dependencies {
    api(libs.androidx.navigation3.runtime)

    api(libs.kotlinx.collectionsimmutable)

    implementation(project(":core:metro-common"))

    implementation(libs.jetbrains.compose.ui.util)
    implementation(libs.jetbrains.lifecycle.viewmodel.navigation3)
  }
}
