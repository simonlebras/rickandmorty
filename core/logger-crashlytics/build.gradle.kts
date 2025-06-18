plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.logger.crashlytics" }

  sourceSets {
    androidMain { dependencies { implementation(libs.kermit.crashlytics) } }

    commonMain {
      dependencies {
        api(project(":core:startup"))

        implementation(libs.kermit)
      }
    }

    nativeMain { dependencies { implementation(libs.kermit.crashlytics) } }
  }
}
