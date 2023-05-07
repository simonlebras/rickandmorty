plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.coroutines"
}

androidComponents {
    beforeVariants(selector().withBuildType("debug")) { builder ->
        builder.enable = false
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    runtimeOnly(libs.kotlinx.coroutines.android)
}
