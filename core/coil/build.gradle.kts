plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.coil"
}

dependencies {
    api(libs.coil)

    implementation(libs.androidx.core)

    implementation(projects.core.hilt)
}
