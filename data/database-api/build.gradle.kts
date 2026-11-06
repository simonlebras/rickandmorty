plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  dependencies {
    api(libs.androidx.room.common)

    api(libs.kotlinx.coroutines.core)
  }
}
