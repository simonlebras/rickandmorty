plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(project(":core:startup"))

        implementation(compose.runtime)
      }
    }
  }
}
