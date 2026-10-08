plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.crashlytics" }

  dependencies {
    api(project(":core:startup"))

    implementation(project(":core:base"))
  }

  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":core:metro-common"))

        implementation(project.dependencies.platform(libs.firebase.bom))
        implementation(libs.crashkios.crashlytics)
        implementation(libs.firebase.crashlytics)
      }
    }

    nativeMain {
      dependencies { implementation(libs.crashkios.crashlytics) }
    }
  }
}
