package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.configureSpotless
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class SpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.nodeGradle.get().pluginId)
            apply(libs.plugins.spotless.get().pluginId)
        }

        configure<SpotlessExtension> {
            configureSpotless()
        }
    }
}
