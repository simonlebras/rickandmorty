plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  dependencies {
    api(project(":core:coroutines"))

    api(project(":data:license-api"))

    implementation(libs.aboutlibraries.core)
  }
}
