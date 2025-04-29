plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.kotlininject.anvil)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.data.theme" }

  sourceSets {
    androidMain {
      dependencies {
        implementation(libs.androidx.appcompat)

        implementation(projects.core.processLifecycle)
      }
    }

    commonMain {
      dependencies {
        api(projects.data.model)

        implementation(libs.androidx.datastore)

        implementation(libs.kotlinx.collectionsimmutable)

        implementation(projects.core.base)
        implementation(projects.core.coroutines)
        implementation(projects.core.injectAnnotations)
        implementation(projects.core.startup)

        implementation(projects.data.themeProto)
      }
    }
  }
}
