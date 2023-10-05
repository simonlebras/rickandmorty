plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
}

android {
    namespace = "app.rickandmorty.okhttp"
}

dependencies {
    api(libs.okhttp)

    debugImplementation(libs.okhttp.logginginterceptor)
}
