plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.apollo)
  alias(libs.plugins.metro)
}

kotlin {
  dependencies {
    api(project(":data:episode-api"))

    api(libs.androidx.paging.common)

    implementation(project(":core:paging"))

    implementation(project(":data:database-api"))
    implementation(project(":data:graphql-schema"))

    implementation(libs.apollo.runtime)
  }
}

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.episode"
    dependsOn(project(":data:graphql-schema"))
  }
}
