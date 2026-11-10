plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.logger.crashlytics" }

  dependencies { implementation(libs.kermit) }

  sourceSets {
    androidMain {
      dependencies {
        api(project(":core:startup"))

        implementation(libs.kermit.crashlytics)
      }
    }

    nativeMain {
      dependencies {
        api(project(":core:startup"))

        implementation(libs.kermit.crashlytics)
      }
    }
  }
}

dependencyAnalysis {
  issues {
    onUnusedDependencies { exclude(":core:startup") }
  }
}
