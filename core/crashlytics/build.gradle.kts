plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.crashlytics" }

  sourceSets {
    androidMain { dependencies { implementation(libs.crashkios.crashlytics) } }

    commonMain { dependencies { api(project(":core:startup")) } }

    nativeMain { dependencies { implementation(libs.crashkios.crashlytics) } }
  }
}
