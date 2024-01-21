plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.core.coil"
}

dependencies {
    implementation(libs.androidx.core)

    implementation(libs.coil.core)
    implementation(libs.coil.network.okhttp)
}
