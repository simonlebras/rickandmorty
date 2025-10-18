import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.crashlytics" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class) dependencies { api(project(":core:startup")) }

  sourceSets {
    androidMain { dependencies { implementation(libs.crashkios.crashlytics) } }

    nativeMain { dependencies { implementation(libs.crashkios.crashlytics) } }
  }
}
