package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.ksp
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class ShowkasePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(libs.plugins.ksp.get().pluginId)

        dependencies {
            implementation(libs.showkase)
            ksp(libs.showkase.processor)
        }
    }
}
