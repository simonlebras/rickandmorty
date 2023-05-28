package app.rickandmorty.gradle.utils

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

context(Project)
internal fun BaseExtension.configureAndroid(libs: LibrariesForLibs) {
    compileSdkVersion(libs.versions.androidCompileSdk.get().toInt())

    defaultConfig.apply {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
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
