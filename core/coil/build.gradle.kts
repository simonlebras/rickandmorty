plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.core.coil"

    buildTypes {
        val release by getting {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.androidx.core)

    implementation(libs.coil.core)
    implementation(libs.coil.network)

    implementation(libs.ktor.okhttp)
}
