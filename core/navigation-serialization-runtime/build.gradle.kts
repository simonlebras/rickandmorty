import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(libs.androidx.navigation3.runtime)

    api(libs.kotlinx.serialization.core)

    implementation(libs.metro.runtime)
  }
}
