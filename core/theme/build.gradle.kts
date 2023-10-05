plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
}

android {
    namespace = "app.rickandmorty.theme"
}

dependencies {
    api(projects.core.coroutines)
    api(projects.core.startup)
    api(projects.core.themeProto)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.datastore)

    implementation(libs.kotlinx.collectionsimmutable)
}
