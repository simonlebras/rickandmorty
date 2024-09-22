package app.rickandmorty.gradle.util

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid(
    baseExtension: BaseExtension,
    libs: LibrariesForLibs,
) {
    with(baseExtension) {
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
            val javaTarget = libs.versions.java.target.get()
            sourceCompatibility(javaTarget)
            targetCompatibility(javaTarget)

            isCoreLibraryDesugaringEnabled = true
        }

        buildTypes {
            named("debug") {
                // For upstream Android libraries that just have a single release variant
                matchingFallbacks += "release"
            }
        }
    }

    dependencies {
        coreLibraryDesugaring(libs.android.desugarjdklibs)

        lintChecks(libs.android.security.lints)
    }
}
