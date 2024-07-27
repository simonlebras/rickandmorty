package app.rickandmorty.gradle.plugin

import app.cash.licensee.LicenseeExtension
import app.cash.licensee.LicenseeTask
import app.rickandmorty.gradle.util.androidComponentsExtension
import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.capitalize
import app.rickandmorty.gradle.util.withPlugins
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the

public class LicenseePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.licensee)

            withPlugins(
                libs.plugins.android.application,
                libs.plugins.android.library,
            ) {
                androidComponentsExtension.onVariants { variant ->
                    val capitalizedVariantName = variant.name.capitalize()
                    val licenseeTask = tasks.named<LicenseeTask>(
                        "licenseeAndroid$capitalizedVariantName",
                    )
                    val copyArtifactsTask = tasks.register<Copy>(
                        "copy${capitalizedVariantName}Artifacts",
                    ) {
                        from(licenseeTask.map { it.jsonOutput })
                        into(layout.buildDirectory.dir("generated/dependencyAssets/${variant.name}"))

                        dependsOn(licenseeTask)
                    }

                    variant.sources.assets?.addGeneratedSourceDirectory(licenseeTask) {
                        objects
                            .directoryProperty()
                            .fileProvider(copyArtifactsTask.map { it.destinationDir })
                    }
                }
            }
        }

        configure<LicenseeExtension> {
            allow("Apache-2.0")
            allow("MIT")
            allowUrl("https://raw.githubusercontent.com/apollographql/apollo-kotlin/main/LICENSE")
            allowUrl("https://github.com/ansman/auto-dagger/blob/1.2.1/LICENSE.txt")
        }
    }
}
