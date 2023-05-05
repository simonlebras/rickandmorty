plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.compose")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.jankstats"
}

androidComponents {
    beforeVariants(selector().withBuildType("debug")) { builder ->
        builder.enable = false
    }
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.metrics)

    implementation(libs.androidx.compose.ui)
}
