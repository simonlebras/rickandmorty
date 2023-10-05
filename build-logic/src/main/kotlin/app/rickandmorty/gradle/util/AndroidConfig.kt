package app.rickandmorty.gradle.util

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

context(Project)
internal fun BaseExtension.configureAndroid(libs: LibrariesForLibs) {
    compileSdkVersion(libs.versions.android.sdk.compile.get().toInt())

    defaultConfig.apply {
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()
    }

    testOptions {
        animationsDisabled = true

        configureGradleManagedDevices()
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
