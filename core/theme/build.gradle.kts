plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.theme"
}

dependencies {
    api(projects.core.coroutines)
    api(projects.core.startup)
    api(projects.core.themeProto)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.datastore)

    implementation(libs.kotlinx.collectionsimmutable)

    compileOnly(libs.compose.stablemarker)
}
