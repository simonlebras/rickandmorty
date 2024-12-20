plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.kotlin.multiplatform)
    alias(libs.plugins.rickandmorty.kotlininject.anvil)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.core.coil"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.coil.core)
                implementation(libs.coil.network.ktor3)

                implementation(projects.core.injectAnnotations)
            }
        }
    }
}
