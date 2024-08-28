package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureSpotless
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

public class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.dependencyanalysis,
            libs.plugins.sortdependencies,
        )

        configureSpotless(libs)
    }
}
