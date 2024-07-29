plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.autodagger)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)

    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "app.rickandmorty.data.license"
}

dependencies {
    api(libs.kotlinx.collectionsimmutable)

    api(projects.data.model)

    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.coroutines)
}
