plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.autodagger)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.contentview"
}

dependencies {
    debugApi(libs.debugdrawer.okhttplogger)

    debugApi(projects.core.okhttp)

    debugImplementation(libs.debugdrawer)
    debugImplementation(libs.debugdrawer.timber)
}
