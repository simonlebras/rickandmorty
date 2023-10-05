plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
}

android {
    namespace = "app.rickandmorty.feature.home"
}

dependencies {
    api(libs.androidx.compose.material3.windowsizeclass)

    api(libs.kotlinx.collectionsimmutable)

    api(libs.microsoft.compose.twopanelayout)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.navigation.compose)

    implementation(projects.core.ui)
}
