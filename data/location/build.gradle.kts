plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.apollo)
  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android { namespace = "app.rickandmorty.data.location" }

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.location"
    dependsOn(project(":data:graphql-schema"))
  }
}

dependencies {
  api(project(":data:model"))

  api(libs.androidx.paging.common)

  implementation(project(":data:database"))
  implementation(project(":data:graphql-schema"))
  implementation(project(":data:paging"))

  implementation(libs.apollo.runtime)
}
