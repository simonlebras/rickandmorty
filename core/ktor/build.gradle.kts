plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
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
