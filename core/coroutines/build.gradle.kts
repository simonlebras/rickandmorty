plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.core.coroutines"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(projects.core.base)

    runtimeOnly(libs.kotlinx.coroutines.android)
}
