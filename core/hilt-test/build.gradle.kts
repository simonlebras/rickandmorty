plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.core.hilt.test"
}

dependencies {
    implementation(libs.androidx.test.runner)

    implementation(libs.hilt.android.testing)
}
