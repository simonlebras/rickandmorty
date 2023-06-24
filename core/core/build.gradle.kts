plugins {
    id("app.rickandmorty.android-library")
}

android {
    namespace = "app.rickandmorty.core"
}

dependencies {
    api(libs.kotlinx.coroutines.core)

    implementation(libs.hilt.android)
    implementation(libs.hilt.core)
}
