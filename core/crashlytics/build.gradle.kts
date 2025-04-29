plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.kotlininject.anvil)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.crashlytics" }

  sourceSets {
    androidMain { dependencies { implementation(libs.crashkios.crashlytics) } }

    commonMain { dependencies { api(projects.core.startup) } }

    nativeMain { dependencies { implementation(libs.crashkios.crashlytics) } }
  }
}
