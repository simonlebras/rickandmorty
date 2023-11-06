plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.okhttp"
}

dependencies {
    api(libs.okhttp)

    debugApi(projects.core.logging)

    debugImplementation(libs.okhttp.logginginterceptor)
}
