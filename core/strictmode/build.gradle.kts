plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.strictmode"
}

dependencies {
    api(projects.core.logging)
    api(projects.core.startup)
}
