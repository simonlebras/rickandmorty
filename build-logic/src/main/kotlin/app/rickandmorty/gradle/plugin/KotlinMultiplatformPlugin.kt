package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureKotlinCommon
import app.rickandmorty.gradle.util.withPlugin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public class KotlinMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(libs.plugins.kotlin.multiplatform)

        configureKotlinCommon(libs)

        val javaTarget = libs.versions.java.target.get()

        extensions.configure<KotlinMultiplatformExtension> {
            applyDefaultHierarchyTemplate()

            pluginManager.withPlugin(libs.plugins.android.library) {
                androidTarget()
            }

            jvm()

            iosArm64()
            iosSimulatorArm64()

            targets.withType<KotlinJvmTarget> {
                compilations.configureEach {
                    compileTaskProvider.configure {
                        compilerOptions {
                            freeCompilerArgs.addAll(
                                "-Xjdk-release=$javaTarget",
                            )
                        }
                    }
                }
            }

            explicitApi()
        }

        tasks.withType<KotlinCompilationTask<*>>().configureEach {
            compilerOptions {
                freeCompilerArgs.addAll(
                    "-Xexpect-actual-classes",
                )
            }
        }
    }
}
