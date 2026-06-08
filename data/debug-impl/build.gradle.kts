plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  dependencies {
    api(project(":core:coroutines"))
    api(project(":core:datastore"))

    api(project(":data:debug-api"))

    api(libs.kotlinx.serialization.protobuf)
  }
}
