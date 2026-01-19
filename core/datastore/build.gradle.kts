plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.datastore" }

  dependencies {
    api(libs.androidx.datastore)

    api(libs.okio)

    implementation(libs.kotlinx.serialization.protobuf)
  }

  sourceSets { androidMain { dependencies { implementation(project(":core:metro-common")) } } }
}
