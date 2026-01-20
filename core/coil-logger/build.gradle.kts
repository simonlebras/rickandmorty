plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  dependencies {
    implementation(libs.coil.core)

    implementation(libs.kermit)
  }
}
