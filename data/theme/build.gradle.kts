plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.autodagger)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.data.theme"
}

dependencies {
    api(projects.data.model)
    api(projects.data.themeProto)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.datastore)

    implementation(libs.kotlinx.collectionsimmutable)

    implementation(projects.core.base)
    implementation(projects.core.coroutines)
    implementation(projects.core.startup)
}
