import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.coroutines" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies { implementation(libs.kotlinx.coroutines.core) }

  sourceSets { androidMain { dependencies { implementation(project(":core:base")) } } }
}
