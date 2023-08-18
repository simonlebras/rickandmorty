plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.service.locale"
}

dependencies {
    api(libs.kotlinx.collectionsimmutable)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core)

    implementation(projects.core.coroutines)
}
