package app.rickandmorty.gradle.utils

import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

context(Project)
internal fun BaseExtension.configureAndroid(libs: LibrariesForLibs) {
    compileSdkVersion(libs.versions.android.sdk.compile.get().toInt())

    defaultConfig.apply {
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()

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
        coreLibraryDesugaring(libs.android.desugarjdklibs)
    }
}
