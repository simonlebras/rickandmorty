plugins {
    id("app.rickandmorty.android-application")
    id("app.rickandmorty.compose")
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
    implementation(libs.androidx.activity.compose)
}
