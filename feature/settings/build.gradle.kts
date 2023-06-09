plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.settings"
}

dependencies {
    api(libs.androidx.navigation.compose)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.kotlinx.collections.immutable)

    implementation(projects.core.coroutines)
    implementation(projects.core.designsystem)
    implementation(projects.core.jankstats)
    implementation(projects.core.locale)
    implementation(projects.core.resourceState)
    implementation(projects.core.ui)
    implementation(projects.core.uiResources)
}
