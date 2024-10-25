plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.kotlin.multiplatform)
    alias(libs.plugins.rickandmorty.kotlininject.anvil)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.core.strictmode"
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.kermit)

                implementation(projects.core.base)
                implementation(projects.core.startup)
            }
        }
    }
}
