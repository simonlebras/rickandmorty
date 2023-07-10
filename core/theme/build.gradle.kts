plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.theme"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.startup)

    implementation(libs.kotlinx.collections.immutable)

    implementation(projects.core.coroutines)
    implementation(projects.core.themeProto)
}
