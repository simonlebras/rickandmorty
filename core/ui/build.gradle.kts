plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.core.ui"
}

dependencies {
    api(libs.androidx.activity)
    api(libs.androidx.paging.compose)
    api(libs.androidx.window)

    api(projects.data.model)

    implementation(projects.core.designSystem)
    implementation(projects.core.l10n)
}
