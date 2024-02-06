plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.core.ui"
}

dependencies {
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.paging.compose)
    api(libs.androidx.window)

    api(projects.data.model)

    implementation(projects.core.uiResources)
}
