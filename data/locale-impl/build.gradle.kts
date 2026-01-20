plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.data.locale" }

  dependencies {
    api(project(":data:locale-api"))

    implementation(project(":core:coroutines"))
  }

  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":core:metro-common"))

        implementation(libs.androidx.appcompat)
      }
    }
  }
}
