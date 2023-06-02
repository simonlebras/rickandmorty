plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.showkase")
}

android {
    namespace = "app.rickandmorty.catalog"
}

dependencies {
    implementation(libs.showkase.annotation)
}
