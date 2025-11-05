import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":core:metro-common"))

    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
  }
}
