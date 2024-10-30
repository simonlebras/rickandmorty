package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.ksp
import app.rickandmorty.gradle.util.kspDependencies
import app.rickandmorty.gradle.util.withPlugin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class KotlinInjectAnvilPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.ksp)

            withPlugin(libs.plugins.kotlin.android) {
                configureKotlinInjectAnvilAndroid(libs)
            }

            withPlugin(libs.plugins.kotlin.multiplatform) {
                configureKotlinInjectAnvilMultiplatform(libs)
            }
        }
    }

    private fun Project.configureKotlinInjectAnvilAndroid(libs: LibrariesForLibs) {
        dependencies {
            implementation(libs.kotlininject.anvil.runtime)
            implementation(libs.kotlininject.anvil.runtime.optional)
            implementation(libs.kotlininject.core.runtime)

            ksp(libs.kotlininject.anvil.compiler)
        }
    }

    private fun Project.configureKotlinInjectAnvilMultiplatform(libs: LibrariesForLibs) {
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
