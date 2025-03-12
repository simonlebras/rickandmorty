package app.rickandmorty.gradle.util

import com.android.build.api.dsl.CommonExtension
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    with(commonExtension) {
        compileSdk = libs.versions.android.sdk.compile.get().toInt()

        defaultConfig.apply {
            minSdk = libs.versions.android.sdk.min.get().toInt()
        }

        lint {
            checkReleaseBuilds = false
            warningsAsErrors = true
            disable += setOf(
                "AndroidGradlePluginVersion",
                "GradleDependency",
                "OldTargetApi",
            )
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
