import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  android { namespace = "app.rickandmorty.core.ui" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":data:character-api"))

    api(libs.androidx.paging.compose)

    implementation(project(":core:design-system"))
    implementation(project(":core:l10n"))
  }

  sourceSets {
    androidMain {
      dependencies {
        api(libs.androidx.activity)

        implementation(libs.androidx.activity.compose)
      }
    }
  }
}
