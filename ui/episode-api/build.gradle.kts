plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  dependencies {
    api(libs.androidx.navigation3.runtime)
  }
}
