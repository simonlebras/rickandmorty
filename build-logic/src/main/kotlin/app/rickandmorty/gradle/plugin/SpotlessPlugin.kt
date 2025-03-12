package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureSpotless
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class SpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply(libs.plugins.spotless)

        configureSpotless()
    }
}
