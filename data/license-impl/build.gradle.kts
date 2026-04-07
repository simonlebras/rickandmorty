plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  android { namespace = "app.rickandmorty.data.license" }

  dependencies {
    api(project(":core:coroutines"))

    api(project(":data:license-api"))

    implementation(libs.kotlinx.serialization.json)
  }

  sourceSets { androidMain { dependencies { implementation(project(":core:metro-common")) } } }
}
