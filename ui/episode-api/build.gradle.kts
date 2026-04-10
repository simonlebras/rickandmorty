plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.navigation.serialization)
}

kotlin { dependencies { api(project(":core:metro-common")) } }
