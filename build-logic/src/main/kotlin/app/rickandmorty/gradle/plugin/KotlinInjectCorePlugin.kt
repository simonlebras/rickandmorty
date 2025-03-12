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

public class KotlinInjectCorePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.ksp)

            withPlugin(libs.plugins.kotlin.android) {
                configureKotlinInjectCoreAndroid()
            }

            withPlugin(libs.plugins.kotlin.multiplatform) {
                configureKotlinInjectCoreMultiplatform()
            }
        }
    }

    private fun Project.configureKotlinInjectCoreAndroid() {
        dependencies {
            implementation(libs.kotlininject.core.runtime)

            ksp(libs.kotlininject.core.compiler)
        }
    }

    private fun Project.configureKotlinInjectCoreMultiplatform() {
        configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain.dependencies {
                implementation(libs.kotlininject.core.runtime)
            }

            kspDependencies {
                ksp(libs.kotlininject.core.compiler)
            }
        }
    }
}
