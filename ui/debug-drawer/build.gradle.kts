plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.ui.debugdrawer" }

  dependencies {
    api(project(":core:app-info"))
    api(project(":core:coroutines"))
    api(project(":core:metro-common"))
    api(project(":core:resource-state"))
    api(project(":core:root-content"))

    api(project(":data:debug-api"))

    api(libs.coil.core)

    implementation(project(":core:design-system"))
    implementation(project(":core:l10n"))

    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.jetbrains.compose.animation)
    implementation(libs.jetbrains.compose.material3)

    implementation(libs.metrox.viewmodel.compose)
  }
}

dependencyAnalysis {
  issues {
    onUnusedDependencies {
      exclude(libs.jetbrains.compose.animation)
      exclude(libs.jetbrains.compose.material3)
    }
  }
}
