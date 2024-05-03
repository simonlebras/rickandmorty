import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

kotlin {
    explicitApi()
}

val javaTarget = libs.versions.java.target.get()

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(javaTarget))

        freeCompilerArgs.addAll(
            "-Xcontext-receivers",
            "-Xjdk-release=$javaTarget",
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = javaTarget
    targetCompatibility = javaTarget
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    compileOnly(libs.affectedmoduledetector.plugin)
    compileOnly(libs.android.plugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.androidx.room.plugin)
    compileOnly(libs.dependencyanalysis.plugin)
    compileOnly(libs.gradledoctor.plugin)
    compileOnly(libs.kotlin.plugin)
    compileOnly(libs.spotless.plugin)
    compileOnly(libs.wire.plugin)

    implementation(libs.truth)

    // Workaround for https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "app.rickandmorty.android-application"
            implementationClass = "app.rickandmorty.gradle.plugin.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "app.rickandmorty.android-library"
            implementationClass = "app.rickandmorty.gradle.plugin.AndroidLibraryPlugin"
        }
        register("androidTest") {
            id = "app.rickandmorty.android-test"
            implementationClass = "app.rickandmorty.gradle.plugin.AndroidTestPlugin"
        }
        register("autodagger") {
            id = "app.rickandmorty.autodagger"
            implementationClass = "app.rickandmorty.gradle.plugin.AutoDaggerPlugin"
        }
        register("compose") {
            id = "app.rickandmorty.compose"
            implementationClass = "app.rickandmorty.gradle.plugin.ComposePlugin"
        }
        register("firebaseCrashlytics") {
            id = "app.rickandmorty.firebase-crashlytics"
            implementationClass = "app.rickandmorty.gradle.plugin.FirebaseCrashlyticsPlugin"
        }
        register("firebasePerf") {
            id = "app.rickandmorty.firebase-perf"
            implementationClass = "app.rickandmorty.gradle.plugin.FirebasePerfPlugin"
        }
        register("hilt") {
            id = "app.rickandmorty.hilt"
            implementationClass = "app.rickandmorty.gradle.plugin.HiltPlugin"
        }
        register("jvmLibrary") {
            id = "app.rickandmorty.jvm-library"
            implementationClass = "app.rickandmorty.gradle.plugin.JvmLibraryPlugin"
        }
        register("kotlinAndroid") {
            id = "app.rickandmorty.kotlin-android"
            implementationClass = "app.rickandmorty.gradle.plugin.KotlinAndroidPlugin"
        }
        register("kotlinJvm") {
            id = "app.rickandmorty.kotlin-jvm"
            implementationClass = "app.rickandmorty.gradle.plugin.KotlinJvmPlugin"
        }
        register("ossLicenses") {
            id = "app.rickandmorty.oss-licenses"
            implementationClass = "app.rickandmorty.gradle.plugin.OssLicensesPlugin"
        }
        register("room") {
            id = "app.rickandmorty.room"
            implementationClass = "app.rickandmorty.gradle.plugin.RoomPlugin"
        }
        register("root") {
            id = "app.rickandmorty.root"
            implementationClass = "app.rickandmorty.gradle.plugin.RootPlugin"
        }
        register("wire") {
            id = "app.rickandmorty.wire"
            implementationClass = "app.rickandmorty.gradle.plugin.WirePlugin"
        }
    }
}

spotless {
    val ktlintVersion = libs.versions.ktlint.get()
    kotlin {
        ktlint(ktlintVersion)
        target("src/**/*.kt")
    }
    kotlinGradle {
        ktlint(ktlintVersion)
        target("*.kts")
    }
}
