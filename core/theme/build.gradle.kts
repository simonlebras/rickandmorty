plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.theme"
}

dependencies {
    implementation(libs.androidx.datastore)

    implementation(projects.core.themeProto)
}
