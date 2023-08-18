plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.feature.settings"
}

dependencies {
    api(libs.androidx.navigation.compose)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.kotlinx.collectionsimmutable)

    implementation(projects.core.coroutines)
    implementation(projects.core.designsystem)
    implementation(projects.core.jankstats)
    implementation(projects.core.resourceState)
    implementation(projects.core.theme)
    implementation(projects.core.ui)
    implementation(projects.core.uiResources)

    implementation(projects.services.locale)
}
