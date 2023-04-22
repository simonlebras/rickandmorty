plugins {
    id("app.rickandmorty.android-application")
    id("app.rickandmorty.compose")
    id("app.rickandmorty.hilt")
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "app.rickandmorty"

    defaultConfig {
        applicationId = "app.rickandmorty"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    baselineProfile(projects.benchmark)

    implementation(libs.androidx.activity.compose)

    implementation(projects.core.coil)
    implementation(projects.core.okhttp)

    runtimeOnly(libs.androidx.profileinstaller)
}
