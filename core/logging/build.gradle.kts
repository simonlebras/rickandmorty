plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.logging"
}

dependencies {
    implementation(libs.timber)

    implementation(projects.core.hilt)
    implementation(projects.core.startup)

    releaseImplementation(platform(libs.firebase.bom))
    releaseImplementation(libs.firebase.crashlytics)
}
