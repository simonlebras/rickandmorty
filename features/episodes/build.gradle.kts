plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.feature.episodes"
}

dependencies {
    api(libs.kotlinx.serialization.core)

    api(projects.data.episode)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(projects.core.designSystem)
    implementation(projects.core.l10n)
    implementation(projects.core.ui)

    implementation(projects.thirdparty.androidxPagingCompose)
}
