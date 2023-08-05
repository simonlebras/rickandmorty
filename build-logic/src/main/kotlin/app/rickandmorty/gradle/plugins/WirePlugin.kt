package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.api
import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.withPlugin
import com.android.build.gradle.LibraryExtension
import com.autonomousapps.DependencyAnalysisSubExtension
import com.squareup.wire.gradle.WireExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class WirePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.wire)

            withPlugin(libs.plugins.dependencyanalysis) {
                configure<DependencyAnalysisSubExtension> {
                    issues {
                        onUnusedDependencies {
                            exclude("com.squareup.wire:wire-runtime")
                        }
                    }
                }
            }

            withPlugin(libs.plugins.android.library) {
                configure<LibraryExtension> {
                    buildTypes {
                        named("release") {
                            consumerProguardFiles("${rootProject.rootDir}/proguard/wire.pro")
                        }
                    }
                }
            }
        }

        configure<WireExtension> {
            kotlin {}
        }

        dependencies {
            api(libs.wire.runtime)
        }
    }
}
