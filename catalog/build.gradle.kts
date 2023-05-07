plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.showkase")
}

android {
    namespace = "app.rickandmorty.catalog"
}

androidComponents {
    beforeVariants(selector().withBuildType("debug")) { builder ->
        builder.enable = false
    }
}

dependencies {
    implementation(libs.showkase.annotation)
}
