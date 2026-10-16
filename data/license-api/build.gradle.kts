plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  dependencies {
    api(libs.androidx.compose.runtime.annotation)

    api(libs.kotlinx.collectionsimmutable)
  }
}
