plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  dependencies {
    api(libs.androidx.navigation3.runtime)

    api(libs.kotlinx.serialization.core)

    implementation(libs.metro.runtime)
  }
}
