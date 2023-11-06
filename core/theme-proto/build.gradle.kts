plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.wire)
}

android {
    namespace = "app.rickandmorty.theme.proto"
}
