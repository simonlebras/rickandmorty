import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.logger.crashlytics" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":core:startup"))

    implementation(libs.kermit)
  }

  sourceSets {
    androidMain { dependencies { implementation(libs.kermit.crashlytics) } }

    nativeMain { dependencies { implementation(libs.kermit.crashlytics) } }
  }
}
