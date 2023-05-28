plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.strictmode"
}

androidComponents {
    beforeVariants(selector().withBuildType("debug")) { builder ->
        builder.enable = false
    }
}

dependencies {
    implementation(libs.androidx.startup)

    implementation(projects.core.logging)
}
