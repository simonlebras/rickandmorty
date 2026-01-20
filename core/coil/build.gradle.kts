plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.coil" }

  dependencies {
    implementation(project(":core:metro-common"))

    implementation(libs.coil.core)
    implementation(libs.coil.network.ktor3)
  }
}
