plugins {
  alias(libs.plugins.rickandmorty.android.library)
}

android {
  namespace = "app.rickandmorty.core.ui.tooling.preview"
}

dependencies {
    api(libs.jetbrains.compose.ui.tooling.preview)

    implementation(project(":core:resources-app"))

    implementation(libs.jetbrains.compose.ui.tooling)
}
