plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.metro" }

  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.jetbrains.lifecycle.viewmodel)
        implementation(libs.jetbrains.lifecycle.viewmodel.savedstate)
      }
    }
  }
}
