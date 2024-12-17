plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.ui.location.list"
}

dependencies {
    api(projects.data.location)

    implementation(libs.androidx.activity.compose)

    implementation(projects.core.designSystem)
    implementation(projects.core.l10n)
    implementation(projects.core.ui)

    implementation(projects.thirdparty.androidxPagingCompose)
}
