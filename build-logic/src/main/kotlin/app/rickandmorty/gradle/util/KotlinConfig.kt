package app.rickandmorty.gradle.util

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureKotlin(libs: LibrariesForLibs) {
    val javaTarget = libs.versions.java.target.get()

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(javaTarget)

            allWarningsAsErrors = true

            freeCompilerArgs.addAll(
                "-Xconsistent-data-class-copy-visibility",
                "-Xjvm-default=all",
            )
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = javaTarget
        targetCompatibility = javaTarget
    }
}
