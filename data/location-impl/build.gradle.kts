plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.apollo)
}

kotlin {
  dependencies {
    api(project(":data:database-api"))
    api(project(":data:location-api"))

    api(libs.apollo.runtime)

    implementation(project(":core:paging"))

    implementation(project(":data:graphql-schema"))
  }
}

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.location"
    dependsOn(project(":data:graphql-schema"))
  }
}
