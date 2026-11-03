plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  dependencies {
    api(libs.jetbrains.compose.runtime)

    api(libs.kotlinx.collectionsimmutable)
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.serialization.core)
  }
}
