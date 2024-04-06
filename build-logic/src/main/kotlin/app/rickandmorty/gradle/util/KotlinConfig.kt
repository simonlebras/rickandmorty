package app.rickandmorty.gradle.util

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(libs: LibrariesForLibs) {
    configureKotlinCommon(libs)

    pluginManager.withPlugin(libs.plugins.android.library) {
        explicitApi()
    }
}

internal fun Project.configureKotlinJvm(libs: LibrariesForLibs) {
    configureKotlinCommon(libs)

    explicitApi()

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            val javaTarget = libs.versions.java.target.get()
            freeCompilerArgs.add("-Xjdk-release=$javaTarget")
        }
    }
}

private fun Project.configureKotlinCommon(libs: LibrariesForLibs) {
    val javaTarget = libs.versions.java.target.get()

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(javaTarget))

            freeCompilerArgs.add("-Xcontext-receivers")
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = javaTarget
        targetCompatibility = javaTarget
    }
}

private fun Project.explicitApi() {
    configure<KotlinProjectExtension> {
        explicitApi()
    }
}
