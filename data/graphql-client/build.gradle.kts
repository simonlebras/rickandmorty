plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.data.graphql.client" }

  sourceSets {
    commonMain {
      dependencies {
        api(libs.apollo.runtime)

        implementation(project(":core:coroutines"))
        implementation(project(":core:metro"))
      }
    }
  }
}
