import com.google.firebase.perf.plugin.FirebasePerfExtension

plugins {
    alias(libs.plugins.rickandmorty.android.application)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.osslicenses)

    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.easylauncher)
    alias(libs.plugins.modulegraphassert)
}

val useFirebase = file("google-services.json").exists()
if (useFirebase) {
    apply(plugin = "app.rickandmorty.firebase-crashlytics")
    apply(plugin = "app.rickandmorty.firebase-perf")
}

val useReleaseKeystore = rootProject.file("keystore/release.jks").exists()

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

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("keystore/debug.jks")
            storePassword = "rickandmorty"
            keyAlias = "debug"
            keyPassword = "rickandmorty"
        }

        create("release") {
            if (useReleaseKeystore) {
                storeFile = rootProject.file("keystore/release.jks")
                storePassword = properties["RAM_RELEASE_KEYSTORE_PWD"]?.toString() ?: ""
                keyAlias = "release"
                keyPassword = properties["RAM_RELEASE_KEY_PWD"]?.toString() ?: ""
            }
        }
    }

    buildTypes {
        val debug by getting {
            signingConfig = signingConfigs["debug"]
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isPseudoLocalesEnabled = true

            if (useFirebase) {
                configure<FirebasePerfExtension> {
                    setInstrumentationEnabled(false)
                }
            }
        }

        val release by getting {
            signingConfig = signingConfigs[if (useReleaseKeystore) "release" else "debug"]
        }
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
    implementation(libs.androidx.compose.material3.windowsizeclass)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window)

    implementation(libs.kotlinx.collectionsimmutable)

    implementation(libs.microsoft.compose.twopanelayout)

    implementation(projects.core.coil)
    implementation(projects.core.core)
    implementation(projects.core.coroutines)
    implementation(projects.core.designsystem)
    implementation(projects.core.jankstats)
    implementation(projects.core.okhttp)
    implementation(projects.core.resourceState)
    implementation(projects.core.startup)
    implementation(projects.core.theme)

    implementation(projects.features.characters)
    implementation(projects.features.episodes)
    implementation(projects.features.home)
    implementation(projects.features.locations)
    implementation(projects.features.settings)

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
        ":app -> :features:.*",
        ":.* -> :core:.*",
    )
}
