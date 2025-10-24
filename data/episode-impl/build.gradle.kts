import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.apollo)
}

kotlin {
  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":data:episode-api"))

    api(libs.androidx.paging.common)

    implementation(project(":data:database-api"))
    implementation(project(":data:graphql-schema"))
    implementation(project(":data:paging"))

    implementation(libs.apollo.runtime)
  }
}

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.episode"
    dependsOn(project(":data:graphql-schema"))
  }
}
