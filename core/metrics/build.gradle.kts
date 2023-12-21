plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.core.metrics"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.metrics)

    api(projects.core.logging)

    implementation(libs.androidx.compose.ui)
}
