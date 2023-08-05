plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
}

android {
    namespace = "app.rickandmorty.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.material.iconsextended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.text.googlefonts)
}
