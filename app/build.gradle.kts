import com.google.firebase.perf.plugin.FirebasePerfExtension

plugins {
    alias(libs.plugins.rickandmorty.android.application)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.kotlininject.anvil)
    alias(libs.plugins.rickandmorty.kotlininject.core)
    alias(libs.plugins.rickandmorty.osslicenses)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.easylauncher)
    alias(libs.plugins.modulegraphassert)
    alias(libs.plugins.sortdependencies)
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
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3.adaptive.navigationsuite)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.coil)

    implementation(projects.core.base)
    implementation(projects.core.coil)
    implementation(projects.core.coroutines)
    implementation(projects.core.designSystem)
    implementation(projects.core.injectAnnotations)
    implementation(projects.core.ktor)
    implementation(projects.core.l10n)
    implementation(projects.core.resourceState)
    implementation(projects.core.startup)
    implementation(projects.core.ui)

    implementation(projects.data.graphqlClient)
    implementation(projects.data.theme)

    implementation(projects.features.characters)
    implementation(projects.features.episodes)
    implementation(projects.features.locations)
    implementation(projects.features.settings)

    releaseImplementation(projects.core.crashlytics)
    releaseImplementation(projects.core.loggerCrashlytics)

    debugImplementation(projects.core.coilLogger)
    debugImplementation(projects.core.loggerDebug)
    debugImplementation(projects.core.strictMode)

    runtimeOnly(libs.androidx.compose.runtime.tracing)
    runtimeOnly(libs.androidx.profileinstaller)

    runtimeOnly(libs.leakcanary.plumber)

    debugRuntimeOnly(libs.leakcanary)

    baselineProfile(projects.baselineProfile)
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
