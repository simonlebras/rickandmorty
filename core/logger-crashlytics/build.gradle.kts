plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.kotlininject.anvil)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android { namespace = "app.rickandmorty.core.logger.crashlytics" }

kotlin {
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
