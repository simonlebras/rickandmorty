plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
}

android {
    namespace = "app.rickandmorty.feature.locations"
}

dependencies {
    api(libs.microsoft.compose.twopanelayout)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
}
