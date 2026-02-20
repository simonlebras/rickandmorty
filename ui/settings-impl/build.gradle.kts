plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.ui.settings" }

  dependencies {
    api(project(":core:app-info"))
    api(project(":core:navigation"))
    api(project(":core:resource-state"))

    api(project(":data:locale-api"))
    api(project(":data:theme-api"))

    api(project(":ui:settings-api"))

    implementation(project(":core:coroutines"))
    implementation(project(":core:design-system"))
    implementation(project(":core:l10n"))
    implementation(project(":core:metro-common"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.paging.compose)

    implementation(libs.jetbrains.compose.material3.adaptive.navigation3)

    implementation(libs.metrox.viewmodel.compose)
  }
}
