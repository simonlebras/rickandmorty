plugins {
    id("app.rickandmorty.android-test")
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "app.rickandmorty.baselineprofile"

    targetProjectPath = ":app"
}

baselineProfile {
    managedDevices += "pixel6proapi33google"
    useConnectedDevices = false
}

dependencies {
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.test.ext.junit)

    implementation(libs.junit4)
}
