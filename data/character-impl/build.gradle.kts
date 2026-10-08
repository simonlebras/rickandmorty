plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  dependencies {
    api(project(":data:character-api"))
    api(project(":data:database-api"))

    implementation(project(":core:paging"))

    implementation(project(":data:network"))
  }
}
