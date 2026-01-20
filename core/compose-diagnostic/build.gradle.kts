plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  dependencies {
    implementation(project(":core:startup"))

    implementation(libs.jetbrains.compose.runtime)
  }
}
