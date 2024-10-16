plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.material.iconsextended)
    implementation(libs.androidx.compose.ui.text.googlefonts)

    implementation(libs.coil.compose)
}
