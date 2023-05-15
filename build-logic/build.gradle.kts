import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
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
        register("android-application") {
            id = "app.rickandmorty.android-application"
            implementationClass = "app.rickandmorty.gradle.plugins.AndroidApplicationPlugin"
        }
        register("android-library") {
            id = "app.rickandmorty.android-library"
            implementationClass = "app.rickandmorty.gradle.plugins.AndroidLibraryPlugin"
        }
        register("android-test") {
            id = "app.rickandmorty.android-test"
            implementationClass = "app.rickandmorty.gradle.plugins.AndroidTestPlugin"
        }
        register("compose") {
            id = "app.rickandmorty.compose"
            implementationClass = "app.rickandmorty.gradle.plugins.ComposePlugin"
        }
        register("firebase-crashlytics") {
            id = "app.rickandmorty.firebase-crashlytics"
            implementationClass = "app.rickandmorty.gradle.plugins.FirebaseCrashlyticsPlugin"
        }
        register("firebase-perf") {
            id = "app.rickandmorty.firebase-perf"
            implementationClass = "app.rickandmorty.gradle.plugins.FirebasePerfPlugin"
        }
        register("hilt") {
            id = "app.rickandmorty.hilt"
            implementationClass = "app.rickandmorty.gradle.plugins.HiltPlugin"
        }
        register("jvm-library") {
            id = "app.rickandmorty.jvm-library"
            implementationClass = "app.rickandmorty.gradle.plugins.JvmLibraryPlugin"
        }
        register("oss-licenses") {
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
        register("showkase") {
            id = "app.rickandmorty.showkase"
            implementationClass = "app.rickandmorty.gradle.plugins.ShowkasePlugin"
        }
        register("spotless") {
            id = "app.rickandmorty.spotless"
            implementationClass = "app.rickandmorty.gradle.plugins.SpotlessPlugin"
        }
    }
}
