plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "androidx.paging.compose" }

  sourceSets {
    commonMain {
      dependencies {
        api(compose.runtime)

        api(libs.androidx.paging.common)

        implementation(compose.foundation)
        implementation(compose.ui)
      }
    }
  }
}
