import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  android { namespace = "app.rickandmorty.core.ui.tooling.preview" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(libs.jetbrains.compose.ui.tooling.preview)

    implementation(libs.coil.compose)

    implementation(libs.jetbrains.compose.material3)
  }

  sourceSets {
    androidMain {
      dependencies {
        api(libs.jetbrains.compose.ui.tooling)

        api(libs.androidx.activity.compose)
        api(libs.androidx.customview.poolingcontainer)
        api(libs.androidx.emoji2)

        implementation(project(":core:resources-app"))
      }
    }
  }
}
