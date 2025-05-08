plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.metro)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.logger.crashlytics" }

  sourceSets {
    androidMain { dependencies { implementation(libs.kermit.crashlytics) } }

    commonMain {
      dependencies {
        api(projects.core.startup)

        implementation(libs.kermit)
      }
    }

    nativeMain { dependencies { implementation(libs.kermit.crashlytics) } }
  }
}
