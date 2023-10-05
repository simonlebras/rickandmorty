plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
}

android {
    namespace = "app.rickandmorty.coroutines"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    runtimeOnly(libs.kotlinx.coroutines.android)
}
