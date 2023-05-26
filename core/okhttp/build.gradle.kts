plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.okhttp"
}

dependencies {
    api(libs.okhttp)

    debugImplementation(libs.okhttp.loggingInterceptor)
}
