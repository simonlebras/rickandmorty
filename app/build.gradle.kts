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
            isPseudoLocalesEnabled = true
        }

        val release by getting

        val qa by creating {
            initWith(release)
            matchingFallbacks += listOf("release")
        }
    }
}

val qaImplementation by configurations

dependencies {
    baselineProfile(projects.benchmark)

    implementation(libs.androidx.activity.compose)

    implementation(projects.core.coil)
    implementation(projects.core.okhttp)

    qaImplementation(projects.core.coilLogging)
    qaImplementation(projects.core.okhttpLogging)

    debugImplementation(projects.core.coilLogging)
    debugImplementation(projects.core.okhttpLogging)

    runtimeOnly(libs.androidx.profileinstaller)

    runtimeOnly(libs.leakcanary.plumber)

    debugRuntimeOnly(libs.leakcanary)
}

easylauncher {
    buildTypes {
        val debug by creating {
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
