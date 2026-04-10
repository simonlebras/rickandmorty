plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.ui.settings" }

  dependencies {
    api(project(":core:app-info"))
    api(project(":core:metro-common"))
    api(project(":core:navigation"))
    api(project(":core:resource-state"))

    api(project(":data:license-api"))
    api(project(":data:locale-api"))
    api(project(":data:theme-api"))

    api(project(":ui:settings-api"))

    implementation(project(":core:design-system"))
    implementation(project(":core:l10n"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.jetbrains.compose.material3.adaptive.navigation3)

    implementation(libs.metrox.viewmodel.compose)
  }
}

dependencyAnalysis {
  issues { onUnusedDependencies { exclude(libs.jetbrains.compose.material3.adaptive.navigation3) } }
}
