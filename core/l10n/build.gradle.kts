plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(compose.components.resources)

        implementation(compose.runtime)
      }
    }
  }
}

compose.resources {
  packageOfResClass = "app.rickandmorty.core.l10n.resources"
  publicResClass = true
}
