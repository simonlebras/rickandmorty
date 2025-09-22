plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(project(":data:model"))
        api(project(":data:paging"))

        api(libs.androidx.room.common)
      }
    }
  }
}
