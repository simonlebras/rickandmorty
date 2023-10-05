package app.rickandmorty.gradle.util

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(libs: LibrariesForLibs) {
    configureKotlin(libs)
    pluginManager.withPlugin(libs.plugins.android.library) {
        explicitApi()
    }
}

internal fun Project.configureKotlinJvm(libs: LibrariesForLibs) {
    configureKotlin(libs)
    explicitApi()
}

private fun Project.configureKotlin(libs: LibrariesForLibs) {
    configure<KotlinProjectExtension> {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xcontext-receivers")
        }
    }
}

private fun Project.explicitApi() {
    configure<KotlinProjectExtension> {
        explicitApi()
    }
}
