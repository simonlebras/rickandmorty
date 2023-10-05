plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
}

android {
    namespace = "app.rickandmorty.feature.episodes"
}

dependencies {
    api(libs.microsoft.compose.twopanelayout)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
}
