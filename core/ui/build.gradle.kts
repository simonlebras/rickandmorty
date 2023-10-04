plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
}

android {
    namespace = "app.rickandmorty.ui"
}

dependencies {
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.window)
}
