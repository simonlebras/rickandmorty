plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.hilt.test"
}

dependencies {
    implementation(libs.androidx.test.runner)

    implementation(libs.hilt.android.testing)
}
