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

    androidResources {
        generateLocaleConfig = true
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isPseudoLocalesEnabled = true
        }

        val release by getting
    }
}

baselineProfile {
    baselineProfileRulesRewrite = true
    dexLayoutOptimization = true
}

dependencies {
    baselineProfile(projects.baselineprofile)

    implementation(libs.accompanist.adaptive)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window)

    implementation(libs.kotlinx.collections.immutable)

    implementation(projects.core.coil)
    implementation(projects.core.designsystem)
    implementation(projects.core.hilt)
    implementation(projects.core.jankstats)
    implementation(projects.core.okhttp)

    implementation(projects.feature.characters)
    implementation(projects.feature.episodes)
    implementation(projects.feature.locations)

    runtimeOnly(libs.androidx.profileinstaller)

    runtimeOnly(libs.leakcanary.plumber)

    debugRuntimeOnly(libs.leakcanary)

    debugRuntimeOnly(projects.core.strictmode)
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
