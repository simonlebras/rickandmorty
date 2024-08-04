package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.androidExtension
import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.screenshotTestImplementation
import app.rickandmorty.gradle.util.withPlugins
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class ComposeScreenshotPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.android.compose.screenshot)

            withPlugins(
                libs.plugins.android.application,
                libs.plugins.android.library,
            ) {
                androidExtension.finalizeDsl { extension ->
                    extension.experimentalProperties["android.experimental.enableScreenshotTest"] = true
                }
            }
        }

        dependencies {
            implementation(platform(libs.androidx.compose.bom))
            screenshotTestImplementation(libs.androidx.compose.ui.tooling)
        }
    }
}
