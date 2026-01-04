import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.data.locale" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":data:locale-api"))

    implementation(project(":core:coroutines"))
  }

  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":core:metro-common"))

        implementation(libs.androidx.appcompat)
      }
    }
  }
}
