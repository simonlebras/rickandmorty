plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  dependencies {
    api(project(":core:metro-common"))

    api(libs.androidx.navigation3.runtime)

    api(libs.kotlinx.collectionsimmutable)

    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
  }
}

dependencyAnalysis {
  issues {
    onUnusedDependencies {
      exclude(libs.androidx.lifecycle.viewmodel.navigation3, libs.androidx.navigation3.runtime)
    }
  }
}
