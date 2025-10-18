import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    implementation(libs.coil.core)

    implementation(libs.kermit)
  }
}
