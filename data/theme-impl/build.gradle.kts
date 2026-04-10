plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  android { namespace = "app.rickandmorty.data.theme.impl" }

  dependencies {
    api(project(":core:coroutines"))
    api(project(":core:datastore"))

    api(project(":data:theme-api"))

    api(libs.kotlinx.serialization.protobuf)
  }

  sourceSets {
    androidMain {
      dependencies {
        api(project(":core:process-lifecycle"))
        api(project(":core:startup"))

        implementation(project(":core:base"))

        implementation(libs.androidx.appcompat)
      }
    }
  }
}
