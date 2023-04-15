package app.rickandmorty.gradle.util

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin(
    libs: LibrariesForLibs,
    explicitApi: ExplicitApiMode = ExplicitApiMode.Strict,
) {
    configure<KotlinProjectExtension> {
        val javaVersion = libs.versions.java.get().toInt()
        jvmToolchain(javaVersion)
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += buildList {
                add("-Xcontext-receivers")
                if (!name.contains("test", ignoreCase = true)) {
                    add(explicitApi.toCompilerArg())
                }
            }
        }
    }
}
