plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.data.locale" }

  dependencies {
    api(project(":core:coroutines"))

    api(project(":data:locale-api"))
  }

  sourceSets {
    androidMain {
      dependencies {
        api(project(":core:metro-common"))

        implementation(libs.androidx.appcompat)
      }
    }
  }
}
