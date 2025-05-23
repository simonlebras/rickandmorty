plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.metro" }

  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.jetbrains.lifecycle.viewmodel)

        implementation(libs.metro)
      }
    }
  }
}
