plugins {
    alias(libs.plugins.rickandmorty.android.library)
}

android {
    namespace = "app.rickandmorty.core"
}

dependencies {
    api(libs.hilt.android)

    api(libs.kotlinx.coroutines.core)
}
