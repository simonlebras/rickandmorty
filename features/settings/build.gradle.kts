plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.feature.settings"
}

dependencies {
    api(projects.core.locale)

    api(projects.data.theme)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.kotlinx.collectionsimmutable)

    implementation(projects.core.coroutines)
    implementation(projects.core.designsystem)
    implementation(projects.core.jankstats)
    implementation(projects.core.resourceState)
    implementation(projects.core.uiResources)
}
