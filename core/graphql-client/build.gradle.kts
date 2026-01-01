import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.graphql.client" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(libs.apollo.runtime)

    implementation(project(":core:coroutines"))
    implementation(project(":core:metro-common"))
  }
}
