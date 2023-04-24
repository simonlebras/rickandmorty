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
    }

    buildTypes {
        val debug by getting

        val release by getting
    }
}

dependencies {
    baselineProfile(projects.benchmark)

    implementation(libs.androidx.activity.compose)

    implementation(projects.core.coil)
    implementation(projects.core.okhttp)

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
