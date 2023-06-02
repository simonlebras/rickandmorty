package app.rickandmorty.gradle.util

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

context(Project)
internal fun <T> T.configureAndroid(
    libs: LibrariesForLibs,
) where T : CommonExtension<*, *, *, *, *>,
      T : BaseExtension {
    compileSdkPreview = libs.versions.androidCompileSdk.get()

    defaultConfig.apply {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        coreLibraryDesugaring(libs.android.desugarJdkLibs)
    }
}
