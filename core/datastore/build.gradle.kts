plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.datastore" }

  dependencies {
    api(libs.androidx.datastore.core)
    api(libs.androidx.datastore.core.okio)

    api(libs.okio)

    implementation(libs.kotlinx.serialization.protobuf)
  }

  sourceSets {
    androidMain {
      dependencies { api(project(":core:metro-common")) }
    }
  }
}
