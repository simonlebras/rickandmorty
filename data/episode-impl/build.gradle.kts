plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.apollo)
}

kotlin {
  dependencies {
    api(project(":data:database-api"))
    api(project(":data:episode-api"))

    api(libs.apollo.runtime)

    implementation(project(":core:paging"))

    implementation(project(":data:graphql-schema"))
  }
}

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.episode"
    dependsOn(project(":data:graphql-schema"))
  }
}
