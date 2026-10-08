plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  dependencies {
    api(libs.coil.core)

    implementation(project(":core:base"))
    implementation(libs.kermit)
    implementation(libs.kermit.coil)
  }
}
