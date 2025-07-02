plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.compose)
}

android {
  namespace = "app.rickandmorty.core.ui.tooling.preview"
}

dependencies {
    api(compose.components.uiToolingPreview)

    implementation(compose.uiTooling)

    implementation(project(":core:resources-app"))
}
