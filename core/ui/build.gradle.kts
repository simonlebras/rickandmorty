plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.ui" }

  sourceSets {
    androidMain {
      dependencies {
        api(libs.androidx.activity)

        implementation(libs.androidx.activity.compose)
      }
    }

    commonMain {
      dependencies {
        api(project(":data:model"))

        api(libs.androidx.paging.compose)

        implementation(project(":core:design-system"))
        implementation(project(":core:l10n"))
      }
    }
  }
}
