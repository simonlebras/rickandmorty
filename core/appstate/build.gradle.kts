plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.appstate" }

  dependencies {
    api(libs.androidx.appstate.statestore)

    implementation(project(":core:coroutines"))
    implementation(project(":core:metro-common"))
  }

  sourceSets {
    androidMain {
      dependencies {
        api(project(":core:datastore"))
        api(libs.androidx.appstate.statestore.datastore)
      }
    }
  }
}
