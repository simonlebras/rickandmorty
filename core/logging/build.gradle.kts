plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.logging"
}

dependencies {
    implementation(libs.androidx.startup)

    implementation(libs.timber)

    implementation(projects.core.hilt)

    releaseImplementation(platform(libs.firebase.bom))
    releaseImplementation(libs.firebase.crashlytics)
}
