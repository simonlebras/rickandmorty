plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.strictmode" }

  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":core:base"))
        implementation(project(":core:startup"))

        implementation(libs.kermit)
      }
    }
  }
}
