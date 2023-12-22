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

    api(projects.data.localeProto)
    api(projects.data.model)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.datastore)

    implementation(projects.core.coroutines)
    implementation(projects.core.startup)
}
