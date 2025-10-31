import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(libs.ktor.client.core)

    implementation(project(":core:metro-common"))
  }

  sourceSets {
    jvmMain { dependencies { implementation(libs.ktor.client.okhttp) } }

    nativeMain { dependencies { implementation(libs.ktor.client.darwin) } }
  }
}
