import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.lint)
    alias(libs.plugins.spotless)
}

val javaTarget = libs.versions.java.target.get()

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(javaTarget)

        freeCompilerArgs.addAll(
            "-Xjdk-release=$javaTarget",
        )
    }

    explicitApi()
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
    compileOnly(libs.apollo.plugin)
    compileOnly(libs.dependencyanalysis.plugin)
    compileOnly(libs.gradledoctor.plugin)
    compileOnly(libs.kotlin.compose.compiler.plugin)
    compileOnly(libs.kotlin.plugin)
    compileOnly(libs.spotless.plugin)

    implementation(libs.truth)

    // Workaround for https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    lintChecks(libs.androidx.gradle.lints)
}

gradlePlugin {
    plugins {
        register("android-application") {
            id = "app.rickandmorty.android-application"
            implementationClass = "app.rickandmorty.gradle.plugin.AndroidApplicationPlugin"
        }
        register("android-library") {
            id = "app.rickandmorty.android-library"
            implementationClass = "app.rickandmorty.gradle.plugin.AndroidLibraryPlugin"
        }
        register("android-test") {
            id = "app.rickandmorty.android-test"
            implementationClass = "app.rickandmorty.gradle.plugin.AndroidTestPlugin"
        }
        register("apollo") {
            id = "app.rickandmorty.apollo"
            implementationClass = "app.rickandmorty.gradle.plugin.ApolloPlugin"
        }
        register("compose") {
            id = "app.rickandmorty.compose"
            implementationClass = "app.rickandmorty.gradle.plugin.ComposePlugin"
        }
        register("firebase-crashlytics") {
            id = "app.rickandmorty.firebase-crashlytics"
            implementationClass = "app.rickandmorty.gradle.plugin.FirebaseCrashlyticsPlugin"
        }
        register("firebase-perf") {
            id = "app.rickandmorty.firebase-perf"
            implementationClass = "app.rickandmorty.gradle.plugin.FirebasePerfPlugin"
        }
        register("kotlin-android") {
            id = "app.rickandmorty.kotlin-android"
            implementationClass = "app.rickandmorty.gradle.plugin.KotlinAndroidPlugin"
        }
        register("kotlin-multiplatform") {
            id = "app.rickandmorty.kotlin-multiplatform"
            implementationClass = "app.rickandmorty.gradle.plugin.KotlinMultiplatformPlugin"
        }
        register("kotlininject-anvil") {
            id = "app.rickandmorty.kotlininject-anvil"
            implementationClass = "app.rickandmorty.gradle.plugin.KotlinInjectAnvilPlugin"
        }
        register("kotlininject-core") {
            id = "app.rickandmorty.kotlininject-core"
            implementationClass = "app.rickandmorty.gradle.plugin.KotlinInjectCorePlugin"
        }
        register("oss-licenses") {
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
        register("spotless") {
            id = "app.rickandmorty.spotless"
            implementationClass = "app.rickandmorty.gradle.plugin.SpotlessPlugin"
        }
    }
}

spotless {
    val ktlintVersion = libs.versions.ktlint.core.get()

    kotlin {
        ktlint(ktlintVersion)
        target("src/**/*.kt")
    }

    kotlinGradle {
        ktlint(ktlintVersion)
        target("*.kts")
    }
}
