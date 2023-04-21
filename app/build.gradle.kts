plugins {
    id("app.rickandmorty.android-application")
    id("app.rickandmorty.compose")
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

    runtimeOnly(libs.androidx.profileinstaller)
}
