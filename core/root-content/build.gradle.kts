plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  dependencies {
    api(libs.jetbrains.compose.runtime)
  }
}

dependencyAnalysis {
  issues {
    onUnusedDependencies { exclude(libs.jetbrains.compose.runtime) }
  }
}
