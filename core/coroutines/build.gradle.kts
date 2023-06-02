plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.coroutines"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    runtimeOnly(libs.kotlinx.coroutines.android)
}
