plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
}

android {
    namespace = "app.rickandmorty.logging"
}

dependencies {
    api(libs.timber)

    api(projects.core.startup)

    releaseImplementation(platform(libs.firebase.bom))
    releaseImplementation(libs.firebase.crashlytics)
}
