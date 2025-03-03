package app.rickandmorty.gradle.util

import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    libs: LibrariesForLibs,
) {
    with(commonExtension) {
        compileSdk = libs.versions.android.sdk.compile.get().toInt()

        defaultConfig.apply {
            minSdk = libs.versions.android.sdk.min.get().toInt()
        }

        lint {
            warningsAsErrors = true
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
    }

    dependencies {
        coreLibraryDesugaring(libs.android.desugarjdklibs)

        lintChecks(libs.android.security.lints)
    }
}
