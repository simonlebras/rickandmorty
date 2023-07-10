plugins {
    id("app.rickandmorty.android-application")
    id("app.rickandmorty.compose")
    id("app.rickandmorty.hilt")
    id("app.rickandmorty.oss-licenses")
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

    implementation(libs.microsoft.compose.twopanelayout)

    implementation(projects.core.coil)
    implementation(projects.core.core)
    implementation(projects.core.coroutines)
    implementation(projects.core.designsystem)
    implementation(projects.core.hilt)
    implementation(projects.core.jankstats)
    implementation(projects.core.okhttp)
    implementation(projects.core.resourceState)
    implementation(projects.core.theme)

    implementation(projects.feature.characters)
    implementation(projects.feature.episodes)
    implementation(projects.feature.home)
    implementation(projects.feature.locations)
    implementation(projects.feature.settings)

    runtimeOnly(libs.androidx.compose.runtime.tracing)
    runtimeOnly(libs.androidx.profileinstaller)

    runtimeOnly(libs.leakcanary.plumber)

    debugRuntimeOnly(libs.leakcanary)

    debugRuntimeOnly(projects.core.strictmode)
}

dependencyAnalysis {
    issues {
        onUnusedDependencies {
            exclude(
                // Submodules used by Hilt
                // https://github.com/autonomousapps/dependency-analysis-android-gradle-plugin/issues/791
                ":core:coil",
                ":core:jankstats",
                ":core:okhttp",
                ":core:strictmode",
            )
        }
    }
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
