plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
}

android {
    namespace = "app.rickandmorty.designsystem"
}

dependencies {
    api(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.material.iconsextended)
    implementation(libs.androidx.compose.ui.text.googlefonts)
}
