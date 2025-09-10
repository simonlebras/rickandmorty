plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)

  alias(libs.plugins.apollo)
}

android { namespace = "app.rickandmorty.data.episode" }

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.episode"
    dependsOn(project(":data:graphql-schema"))
  }
}

dependencies {
  api(project(":data:model"))

  api(libs.androidx.paging.common)

  implementation(project(":data:database-api"))
  implementation(project(":data:graphql-schema"))
  implementation(project(":data:paging"))

  implementation(libs.apollo.runtime)
}
