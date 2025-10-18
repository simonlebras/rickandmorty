import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.wire)
}

kotlin {
  android { namespace = "app.rickandmorty.data.theme.impl" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":data:theme-api"))

    implementation(project(":core:base"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:metro"))
    implementation(project(":core:startup"))

    implementation(libs.androidx.datastore)

    implementation(libs.kotlinx.collectionsimmutable)
  }

  sourceSets {
    androidMain {
      dependencies {
        implementation(project(":core:process-lifecycle"))

        implementation(libs.androidx.appcompat)
      }
    }
  }
}

wire { kotlin {} }
