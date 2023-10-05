plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
}

android {
    namespace = "app.rickandmorty.strictmode"
}

dependencies {
    api(projects.core.logging)
    api(projects.core.startup)
}
