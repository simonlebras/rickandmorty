package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.implementation
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class FirebaseCrashlyticsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.googleServices.get().pluginId)
            apply(libs.plugins.firebase.crashlytics.get().pluginId)
        }

        dependencies {
            implementation(platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
        }
    }
}
