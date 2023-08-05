package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.implementation
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class FirebasePerfPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.googleservices,
            libs.plugins.firebase.perf,
        )

        dependencies {
            implementation(platform(libs.firebase.bom))
            implementation(libs.firebase.perf)
        }
    }
}
