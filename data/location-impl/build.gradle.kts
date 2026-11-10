plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  dependencies {
    api(project(":data:location-api"))

    implementation(project(":data:database-api"))
    implementation(project(":data:rest"))
    implementation(project(":data:store"))
  }
}
