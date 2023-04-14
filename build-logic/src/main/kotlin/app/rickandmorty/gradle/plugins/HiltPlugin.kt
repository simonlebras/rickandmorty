package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.kapt
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

public class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.kotlin.kapt.get().pluginId)
            apply(libs.plugins.hilt.get().pluginId)
        }

        configure<KaptExtension> {
            correctErrorTypes = true
        }

        dependencies {
            implementation(libs.hilt.android)
            kapt(libs.hilt.android.compiler)
        }
    }
}
