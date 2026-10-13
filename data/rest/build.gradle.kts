plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  dependencies {
    api(project(":core:ktor"))

    api(libs.kotlinx.serialization.json)

    api(libs.ktor.client.core)

    implementation(libs.ktor.client.contentnegotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
  }
}
