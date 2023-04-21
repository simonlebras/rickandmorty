package app.rickandmorty.gradle.util

import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

context(Project)
internal fun BaseExtension.configureAndroid(libs: LibrariesForLibs) {
    compileSdkVersion(libs.versions.androidCompileSdk.get().toInt())

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        animationsDisabled = true

        managedDevices {
            (27..33).forEach { api ->
                devices.register<ManagedVirtualDevice>("pixel6Api${api}Aosp") {
                    device = "Pixel 6"
                    apiLevel = api
                    // ATD is only supported on api 30 at the moment
                    systemImageSource = if (api == 30) "aosp-atd" else "aosp"
                }
            }
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildTypes {
        named("debug") {
            // For upstream Android libraries that just have a single release variant
            matchingFallbacks += "release"
        }
    }

    dependencies {
        coreLibraryDesugaring(libs.android.desugarJdkLibs)
    }
}
