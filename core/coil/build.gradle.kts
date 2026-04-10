plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.coil" }

  dependencies {
    api(project(":core:metro-common"))

    api(libs.coil.core)

    implementation(libs.coil.network.ktor3)
  }
}
