plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  dependencies {
    api(libs.jetbrains.compose.runtime)

    api(libs.kotlinx.serialization.core)
  }
}
