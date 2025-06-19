plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.metro)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.data.graphql.client" }

  sourceSets {
    commonMain {
      dependencies {
        api(libs.apollo.runtime)

        implementation(projects.core.coroutines)
        implementation(projects.core.metro)
      }
    }
  }
}
