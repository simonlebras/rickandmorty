plugins {
    alias(libs.plugins.rickandmorty.android.library)
}

android {
    namespace = "app.rickandmorty.hilt.test"
}

dependencies {
    api(libs.androidx.test.runner)

    implementation(libs.hilt.android.testing)
}
