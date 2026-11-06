plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.snapshottable)
}

kotlin {
  android { namespace = "app.rickandmorty.ui.location" }

  dependencies {
    api(project(":core:coroutines"))
    api(project(":core:metro-common"))
    api(project(":core:navigation"))
    api(project(":core:tiling"))
    api(project(":core:ui"))

    api(project(":data:location-api"))

    api(project(":ui:location-api"))

    api(libs.androidx.lifecycle.viewmodel.savedstate)

    api(libs.mutator.coroutines)

    implementation(project(":core:design-system"))
    implementation(project(":core:l10n"))

    implementation(project(":ui:settings-api"))

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.jetbrains.compose.material3.adaptive.navigation3)

    implementation(libs.metrox.viewmodel.compose)

    implementation(libs.mutator.compose)
  }
}

dependencyAnalysis {
  issues {
    onUnusedDependencies { exclude(libs.jetbrains.compose.material3.adaptive.navigation3) }
  }
}
