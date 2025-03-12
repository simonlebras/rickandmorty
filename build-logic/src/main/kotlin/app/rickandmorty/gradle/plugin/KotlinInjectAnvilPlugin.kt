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

public class KotlinInjectAnvilPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.ksp)

            withPlugin(libs.plugins.kotlin.android) {
                configureKotlinInjectAnvilAndroid()
            }

            withPlugin(libs.plugins.kotlin.multiplatform) {
                configureKotlinInjectAnvilMultiplatform()
            }
        }
    }

    private fun Project.configureKotlinInjectAnvilAndroid() {
        dependencies {
            implementation(libs.kotlininject.anvil.runtime)
            implementation(libs.kotlininject.anvil.runtime.optional)
            implementation(libs.kotlininject.core.runtime)

            ksp(libs.kotlininject.anvil.compiler)
        }
    }

    private fun Project.configureKotlinInjectAnvilMultiplatform() {
        configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain.dependencies {
                implementation(libs.kotlininject.anvil.runtime)
                implementation(libs.kotlininject.anvil.runtime.optional)
                implementation(libs.kotlininject.core.runtime)
            }

            kspDependencies {
                ksp(libs.kotlininject.anvil.compiler)
            }
        }
    }
}
