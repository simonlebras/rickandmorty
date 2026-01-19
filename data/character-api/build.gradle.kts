plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  dependencies {
    api(libs.androidx.paging.common)

    implementation(project(":core:base"))
  }
}
