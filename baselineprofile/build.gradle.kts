plugins {
    id("app.rickandmorty.android-test")
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "app.rickandmorty.baselineprofile"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"
}

baselineProfile {
    managedDevices += "pixel6api31aosp"
    useConnectedDevices = false
}

dependencies {
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.test.runner)

    implementation(libs.junit4)
}
