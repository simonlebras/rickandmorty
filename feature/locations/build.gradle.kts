plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
}

android {
    namespace = "app.rickandmorty.locations"
}

dependencies {
    api(libs.androidx.navigation.compose)
}
