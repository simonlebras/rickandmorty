import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
    explicitApi()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += listOf(
            "-Xcontext-receivers",
        )
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.dependencyAnalysis.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.protobuf.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)

    // Workaround for https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "app.rickandmorty.android-application"
            implementationClass = "app.rickandmorty.gradle.plugins.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "app.rickandmorty.android-library"
            implementationClass = "app.rickandmorty.gradle.plugins.AndroidLibraryPlugin"
        }
        register("androidTest") {
            id = "app.rickandmorty.android-test"
            implementationClass = "app.rickandmorty.gradle.plugins.AndroidTestPlugin"
        }
        register("compose") {
            id = "app.rickandmorty.compose"
            implementationClass = "app.rickandmorty.gradle.plugins.ComposePlugin"
        }
        register("firebaseCrashlytics") {
            id = "app.rickandmorty.firebase-crashlytics"
            implementationClass = "app.rickandmorty.gradle.plugins.FirebaseCrashlyticsPlugin"
        }
        register("firebasePerf") {
            id = "app.rickandmorty.firebase-perf"
            implementationClass = "app.rickandmorty.gradle.plugins.FirebasePerfPlugin"
        }
        register("hilt") {
            id = "app.rickandmorty.hilt"
            implementationClass = "app.rickandmorty.gradle.plugins.HiltPlugin"
        }
        register("jvmLibrary") {
            id = "app.rickandmorty.jvm-library"
            implementationClass = "app.rickandmorty.gradle.plugins.JvmLibraryPlugin"
        }
        register("ossLicenses") {
            id = "app.rickandmorty.oss-licenses"
            implementationClass = "app.rickandmorty.gradle.plugins.OssLicensesPlugin"
        }
        register("protobuf") {
            id = "app.rickandmorty.protobuf"
            implementationClass = "app.rickandmorty.gradle.plugins.ProtobufPlugin"
        }
        register("root") {
            id = "app.rickandmorty.root"
            implementationClass = "app.rickandmorty.gradle.plugins.RootPlugin"
        }
    }
}

spotless {
    kotlin {
        target("src/**/*.kt")
        ktlint()
    }

    kotlinGradle {
        target("*.kts")
        ktlint()
    }
}
