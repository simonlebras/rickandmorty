plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.jankstats"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.metrics)

    api(projects.core.logging)

    implementation(libs.androidx.compose.ui)
}
