plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.feature.characters"
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)

    implementation(projects.core.designSystem)
    implementation(projects.core.uiResources)
}
