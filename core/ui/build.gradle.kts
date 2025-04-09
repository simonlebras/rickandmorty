plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android { namespace = "app.rickandmorty.core.ui" }

kotlin {
  sourceSets {
    androidMain { dependencies { api(libs.androidx.activity) } }

    commonMain {
      dependencies {
        api(projects.data.model)

        api(projects.thirdparty.androidxPagingCompose)

        implementation(projects.core.designSystem)
        implementation(projects.core.l10n)
      }
    }
  }
}
