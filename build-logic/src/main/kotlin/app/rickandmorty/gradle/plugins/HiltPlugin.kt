package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.implementation
import app.rickandmorty.gradle.utils.ksp
import app.rickandmorty.gradle.utils.withPlugin
import com.autonomousapps.DependencyAnalysisSubExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(
                libs.plugins.hilt,
                libs.plugins.ksp,
            )

            withPlugin(libs.plugins.dependencyanalysis) {
                configure<DependencyAnalysisSubExtension> {
                    issues {
                        onUnusedDependencies {
                            exclude("com.google.dagger:hilt-android")
                        }

                        onIncorrectConfiguration() {
                            exclude("com.google.dagger:hilt-android")
                        }
                    }
                }
            }
        }

        dependencies {
            implementation(libs.hilt.android)
            ksp(libs.hilt.android.compiler)
        }
    }
}
