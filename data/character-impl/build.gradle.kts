plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.apollo)
  alias(libs.plugins.metro)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(project(":data:character-api"))

        api(libs.androidx.paging.common)

        implementation(project(":data:database-api"))
        implementation(project(":data:graphql-schema"))
        implementation(project(":data:paging"))

        implementation(libs.apollo.runtime)
      }
    }
  }
}

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.character"
    dependsOn(project(":data:graphql-schema"))
  }
}
