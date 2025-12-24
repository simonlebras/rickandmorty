import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.ui.character" }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":core:navigation"))

    api(project(":data:character-api"))

    api(project(":ui:character-api"))

    implementation(project(":core:design-system"))
    implementation(project(":core:l10n"))
    implementation(project(":core:metro-common"))
    implementation(project(":core:ui"))
    implementation(project(":core:ui-tooling-preview"))

    implementation(libs.androidx.paging.compose)

    implementation(libs.metrox.viewmodel.compose)
  }
}
