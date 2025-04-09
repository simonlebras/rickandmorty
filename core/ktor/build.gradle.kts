plugins {
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.kotlininject.anvil)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(libs.ktor.client.core)

        implementation(projects.core.injectAnnotations)
      }
    }

    jvmMain { dependencies { implementation(libs.ktor.client.okhttp) } }

    nativeMain { dependencies { implementation(libs.ktor.client.darwin) } }
  }
}
