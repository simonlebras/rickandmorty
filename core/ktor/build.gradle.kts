plugins {
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.metro)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(libs.ktor.client.core)

        implementation(project(":core:metro"))
      }
    }

    jvmMain { dependencies { implementation(libs.ktor.client.okhttp) } }

    nativeMain { dependencies { implementation(libs.ktor.client.darwin) } }
  }
}
