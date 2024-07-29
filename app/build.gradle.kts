import com.google.firebase.perf.plugin.FirebasePerfExtension

plugins {
    alias(libs.plugins.rickandmorty.android.application)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.licensee)

    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.easylauncher)
    alias(libs.plugins.kotlin.parcelize)
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

    packaging {
        resources {
            excludes += "DebugProbesKt.bin"
        }
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
                storePassword = providers.gradleProperty("RAM_RELEASE_KEYSTORE_PWD").getOrElse("")
                keyAlias = "release"
                keyPassword = providers.gradleProperty("RAM_RELEASE_KEY_PWD").getOrElse("")
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
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

baselineProfile {
    baselineProfileRulesRewrite = true
    dexLayoutOptimization = true
}

dependencies {
    baselineProfile(projects.baselineProfile)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.adaptive.navigationsuite)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.coil)

    implementation(projects.core.coil)
    implementation(projects.core.coroutines)
    implementation(projects.core.designSystem)
    implementation(projects.core.l10n)
    implementation(projects.core.metrics)
    implementation(projects.core.okhttp)
    implementation(projects.core.resourceState)
    implementation(projects.core.startup)
    implementation(projects.core.ui)

    implementation(projects.data.graphqlClient)
    implementation(projects.data.theme)

    implementation(projects.features.characters)
    implementation(projects.features.episodes)
    implementation(projects.features.locations)
    implementation(projects.features.settings)

    implementation(projects.ui.settingsLicenses)

    runtimeOnly(libs.androidx.compose.runtime.tracing)
    runtimeOnly(libs.androidx.profileinstaller)

    runtimeOnly(libs.leakcanary.plumber)

    debugRuntimeOnly(libs.leakcanary)

    debugRuntimeOnly(projects.core.strictMode)
}

dependencyAnalysis {
    issues {
        onUnusedDependencies {
            exclude(
                // Submodules used by Hilt
                // https://github.com/autonomousapps/dependency-analysis-android-gradle-plugin/issues/791
                ":core:coil",
                ":core:metrics",
                ":core:okhttp",
                ":data:graphql-client",
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
    maxHeight = 5
    allowed = arrayOf(
        ":app -> :features:.*",
        ":app -> :data:.*",
        ":features:.* -> :data:.*",
        ":data:.* -> :data:.*",
        ":core:.* -> :data:model",
        ":.* -> :core:.*",
    )
}
