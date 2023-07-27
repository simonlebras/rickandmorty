plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.strictmode"
}

dependencies {
    implementation(projects.core.hilt)
    implementation(projects.core.logging)
    implementation(projects.core.startup)
}
