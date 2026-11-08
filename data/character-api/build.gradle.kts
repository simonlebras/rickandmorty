plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  dependencies {
    api(project(":core:paging"))

    api(libs.androidx.compose.runtime.annotation)

    api(libs.kotlinx.coroutines.core)

    implementation(project(":core:base"))
  }
}

dependencyAnalysis {
  issues {
    onIncorrectConfiguration { exclude(":core:paging") }
  }
}
