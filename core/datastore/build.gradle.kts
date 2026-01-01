import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.datastore" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(libs.androidx.datastore)

    api(libs.okio)
  }

  sourceSets { androidMain { dependencies { implementation(project(":core:metro-common")) } } }
}
