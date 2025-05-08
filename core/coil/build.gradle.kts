plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.metro)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.coil" }

  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.coil.core)
        implementation(libs.coil.network.ktor3)

        implementation(projects.core.metro)
      }
    }
  }
}
