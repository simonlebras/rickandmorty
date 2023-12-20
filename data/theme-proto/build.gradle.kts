plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.wire)
}

android {
    namespace = "app.rickandmorty.data.theme.proto"

    buildTypes {
        val release by getting {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}
