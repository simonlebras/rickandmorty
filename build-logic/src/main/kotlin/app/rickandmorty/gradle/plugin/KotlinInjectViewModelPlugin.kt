package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.ksp
import app.rickandmorty.gradle.util.kspDependencies
import app.rickandmorty.gradle.util.withPlugin
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class KotlinInjectViewModelPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply(libs.plugins.ksp)

        configureKotlinInjectViewModelAndroid()

        configureKotlinInjectViewModelMultiplatform()
    }

    private fun Project.configureKotlinInjectViewModelAndroid() {
        pluginManager.withPlugin(libs.plugins.kotlin.android) {
            dependencies {
                implementation(libs.kotlininject.viewmodel.runtime)

                ksp(libs.kotlininject.viewmodel.compiler)
            }
        }
    }

    private fun Project.configureKotlinInjectViewModelMultiplatform() {
        pluginManager.withPlugin(libs.plugins.kotlin.multiplatform) {
            configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {
                    implementation(libs.kotlininject.viewmodel.runtime)
                }

                kspDependencies {
                    ksp(libs.kotlininject.viewmodel.compiler)
                }
            }
        }
    }
}
