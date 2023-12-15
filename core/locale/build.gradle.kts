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

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)

    implementation(projects.core.coroutines)
}
