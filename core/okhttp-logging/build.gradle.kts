plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.okhttp.logging"
}

androidComponents {
    beforeVariants(selector().withBuildType("debug")) { builder ->
        builder.enable = false
    }
}

dependencies {
    api(libs.okhttp)

    implementation(libs.okhttp.loggingInterceptor)

    implementation(projects.core.okhttp)
}
