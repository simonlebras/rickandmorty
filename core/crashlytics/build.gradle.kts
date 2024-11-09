plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.kotlin.multiplatform)
    alias(libs.plugins.rickandmorty.kotlininject.anvil)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.core.crashlytics"
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.crashkios.crashlytics)
            }
        }

        commonMain {
            dependencies {
                api(projects.core.startup)
            }
        }

        nativeMain {
            dependencies {
                implementation(libs.crashkios.crashlytics)
            }
        }
    }
}
