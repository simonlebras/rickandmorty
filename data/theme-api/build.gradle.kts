plugins {
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlinx.collectionsimmutable)
        implementation(libs.kotlinx.coroutines.core)
      }
    }
  }
}
