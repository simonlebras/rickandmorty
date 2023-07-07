package app.rickandmorty.gradle.utils

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin(
    libs: LibrariesForLibs,
    explicitApi: Boolean = true,
) {
    configure<KotlinProjectExtension> {
        val javaVersion = libs.versions.java.get().toInt()
        jvmToolchain(javaVersion)

        if (explicitApi && !name.contains("test", ignoreCase = true)) {
            explicitApi()
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xcontext-receivers")
        }
    }
}
