plugins {
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
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
