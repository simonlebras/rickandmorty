plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  dependencies {
    api(project(":core:startup"))

    implementation(libs.kermit)
  }
}
