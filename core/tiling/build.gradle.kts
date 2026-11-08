plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.snapshottable)
}

kotlin {
  dependencies {
    api(project(":core:paging"))

    api(libs.androidx.compose.runtime.annotation)

    api(libs.jetbrains.compose.runtime)

    api(libs.kotlinx.coroutines.core)

    api(libs.tiler)

    implementation(libs.mutator.coroutines)
  }
}

dependencyAnalysis {
  issues {
    onIncorrectConfiguration { exclude(":core:paging") }
    onUnusedDependencies { exclude(libs.jetbrains.compose.runtime) }
  }
}
