package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.implementation
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

public class FirebaseCrashlyticsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply(
            libs.plugins.googleservices,
            libs.plugins.firebase.crashlytics,
        )

        dependencies {
            implementation(platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
        }
    }
}
