plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.okhttp"
}

androidComponents {
    beforeVariants(selector().withBuildType("debug")) { builder ->
        builder.enable = false
    }
}

dependencies {
    api(libs.okhttp)
}
