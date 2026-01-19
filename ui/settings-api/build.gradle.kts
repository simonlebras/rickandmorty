plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.navigation.serialization)
}

kotlin {
  dependencies {
    api(libs.androidx.navigation3.runtime)

    implementation(project(":core:metro-common"))
  }
}
