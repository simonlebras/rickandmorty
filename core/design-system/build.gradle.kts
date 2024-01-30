plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive.navigationsuite)

    implementation(libs.androidx.compose.material.iconsextended)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.ui.text.googlefonts)

    implementation(libs.haze)
    implementation(libs.haze.materials)

    implementation(projects.core.ui)
    implementation(projects.core.uiResources)
}
