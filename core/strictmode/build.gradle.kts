plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.strictmode"
}

dependencies {
    implementation(libs.androidx.startup)

    implementation(projects.core.logging)
}
