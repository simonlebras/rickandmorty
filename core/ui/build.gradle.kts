plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
}

android {
    namespace = "app.rickandmorty.ui"
}

dependencies {
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.window)
}
