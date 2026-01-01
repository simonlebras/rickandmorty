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
    api(project(":data:location-api"))

    api(libs.androidx.paging.common)

    implementation(project(":core:paging"))

    implementation(project(":data:database-api"))
    implementation(project(":data:graphql-schema"))

    implementation(libs.apollo.runtime)
  }
}

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.location"
    dependsOn(project(":data:graphql-schema"))
  }
}
