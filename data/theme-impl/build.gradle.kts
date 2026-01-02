import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  android { namespace = "app.rickandmorty.data.theme.impl" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":core:datastore"))

    api(project(":data:theme-api"))

    api(libs.kotlinx.serialization.protobuf)

    implementation(project(":core:coroutines"))
  }

  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":core:base"))
        implementation(project(":core:metro-common"))
        implementation(project(":core:process-lifecycle"))
        implementation(project(":core:startup"))

        implementation(libs.androidx.appcompat)
      }
    }
  }
}
