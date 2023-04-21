package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.configureKotlin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

public class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply("app.rickandmorty.spotless")
            apply(libs.plugins.kotlin.jvm.get().pluginId)
            apply(libs.plugins.sortDependencies.get().pluginId)
        }

        configureKotlin(libs)
    }
}
