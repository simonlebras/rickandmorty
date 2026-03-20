plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  android { namespace = "app.rickandmorty.core.ui" }

  dependencies {
    api(project(":data:character-api"))

    api(libs.androidx.navigation3.runtime)
    api(libs.androidx.paging.compose)

    api(libs.haze)

    api(libs.jetbrains.compose.material3.adaptivenavigationsuite)
    api(libs.jetbrains.navigation3.ui)

    implementation(project(":core:design-system"))
    implementation(project(":core:l10n"))
    implementation(project(":core:navigation"))

    implementation(libs.jetbrains.compose.foundation.layout)
  }

  sourceSets { androidMain { dependencies { implementation(libs.androidx.activity.compose) } } }
}
