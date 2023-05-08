plugins {
    id("app.rickandmorty.android-application")
    id("app.rickandmorty.compose")
    id("app.rickandmorty.hilt")
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.easylauncher)
    alias(libs.plugins.moduleGraphAssert)
}

android {
    namespace = "app.rickandmorty"

    defaultConfig {
        applicationId = "app.rickandmorty"
        versionCode = 1
        versionName = "1.0"

        resourceConfigurations += listOf("en", "fr")
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isPseudoLocalesEnabled = true
        }

        val release by getting

        val qa by creating {
            initWith(release)
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            matchingFallbacks += listOf("release")
        }
    }
}

val qaImplementation by configurations
val qaRuntimeOnly by configurations

dependencies {
    baselineProfile(projects.baselineprofile)

    implementation(libs.androidx.activity.compose)

    implementation(projects.core.coil)
    implementation(projects.core.jankstats)
    implementation(projects.core.okhttp)

    qaImplementation(projects.core.coilLogging)
    qaImplementation(projects.core.okhttpLogging)

    debugImplementation(projects.core.coilLogging)
    debugImplementation(projects.core.okhttpLogging)

    runtimeOnly(libs.androidx.profileinstaller)

    runtimeOnly(libs.leakcanary.plumber)

    qaRuntimeOnly(projects.core.strictmode)

    debugRuntimeOnly(libs.leakcanary)

    debugRuntimeOnly(projects.core.strictmode)
}

easylauncher {
    buildTypes {
        val debug by creating {
            filters(chromeLike())
        }

        val qa by creating {
            filters(chromeLike())
        }
    }
}

moduleGraphAssert {
    maxHeight = 4
    allowed = arrayOf(
        ":app -> :feature:.*",
        ":.* -> :core:.*",
    )
}
