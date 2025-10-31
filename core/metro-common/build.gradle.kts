import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  android { namespace = "app.rickandmorty.core.metro.common" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    implementation(libs.jetbrains.lifecycle.viewmodel)

    implementation(libs.metro.runtime)
  }
}
