plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
}

android {
    namespace = "app.rickandmorty.coil"
}

dependencies {
    api(libs.coil)

    implementation(libs.androidx.core)
}
