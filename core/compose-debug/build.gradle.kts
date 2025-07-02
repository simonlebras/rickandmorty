plugins {
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.metro)
  alias(libs.plugins.sortdependencies)
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
