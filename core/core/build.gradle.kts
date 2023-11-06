plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.core"
}

dependencies {
    api(libs.hilt.android)

    api(libs.kotlinx.coroutines.core)
}
