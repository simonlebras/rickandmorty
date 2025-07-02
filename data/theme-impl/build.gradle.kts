plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.metro)
  alias(libs.plugins.sortdependencies)
  alias(libs.plugins.wire)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.data.theme.impl" }

  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":core:process-lifecycle"))

        implementation(libs.androidx.appcompat)
      }
    }

    commonMain {
      dependencies {
        api(project(":data:theme-api"))

        implementation(project(":core:base"))
        implementation(project(":core:coroutines"))
        implementation(project(":core:metro"))
        implementation(project(":core:startup"))

        implementation(libs.androidx.datastore)

        implementation(libs.kotlinx.collectionsimmutable)
      }
    }
  }
}

wire { kotlin {} }
