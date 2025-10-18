import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.coil" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    implementation(project(":core:metro"))

    implementation(libs.coil.core)
    implementation(libs.coil.network.ktor3)
  }
}
