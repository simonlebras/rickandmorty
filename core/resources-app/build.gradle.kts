plugins { alias(libs.plugins.rickandmorty.android.library) }

android {
    namespace = "app.rickandmorty.core.resources.app"

    androidResources.enable = true
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
}
