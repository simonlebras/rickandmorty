plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.autodagger)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.locale"
}

dependencies {
    api(libs.kotlinx.collectionsimmutable)

    api(projects.core.core)

    implementation(libs.androidx.appcompat)

    implementation(projects.core.coroutines)
}
