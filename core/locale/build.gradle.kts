plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.locale"
}

dependencies {
    api(libs.kotlinx.collectionsimmutable)

    api(projects.core.core)
    api(projects.core.coroutines)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core)
}
