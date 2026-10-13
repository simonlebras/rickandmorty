plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  dependencies {
    api(project(":data:character-api"))
    api(project(":data:database-api"))
    api(project(":data:rest"))

    implementation(project(":core:paging"))
  }
}
