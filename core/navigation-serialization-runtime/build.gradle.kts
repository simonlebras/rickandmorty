plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  dependencies {
    api(libs.androidx.navigation3.runtime)

    api(libs.metro.runtime)
  }
}

dependencyAnalysis {
  issues { onUnusedDependencies { exclude(libs.androidx.navigation3.runtime) } }
}
