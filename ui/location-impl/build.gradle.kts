plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.ui.location" }

  dependencies {
    api(project(":core:metro-common"))
    api(project(":core:navigation"))

    api(project(":data:location-api"))

    api(project(":ui:location-api"))

    implementation(project(":core:design-system"))
    implementation(project(":core:l10n"))
    implementation(project(":core:ui"))

    implementation(project(":ui:settings-api"))

    implementation(libs.androidx.paging.compose)

    implementation(libs.jetbrains.compose.material3.adaptive.navigation3)

    implementation(libs.metrox.viewmodel.compose)
  }
}

dependencyAnalysis {
  issues { onUnusedDependencies { exclude(libs.jetbrains.compose.material3.adaptive.navigation3) } }
}
