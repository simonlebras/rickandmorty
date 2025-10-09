import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(libs.ktor.client.core)

    implementation(project(":core:metro"))
  }

  sourceSets {
    jvmMain { dependencies { implementation(libs.ktor.client.okhttp) } }

    nativeMain { dependencies { implementation(libs.ktor.client.darwin) } }
  }
}
