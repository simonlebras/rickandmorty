plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
}

android {
    namespace = "app.rickandmorty.feature.home"
}

dependencies {
    api(libs.androidx.compose.material3.windowsizeclass)

    api(libs.androidx.navigation.compose)

    api(libs.kotlinx.collectionsimmutable)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.util)

    implementation(libs.microsoft.compose.twopanelayout)

    implementation(projects.core.ui)
}
