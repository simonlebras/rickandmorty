package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.configureSpotless
import app.rickandmorty.gradle.util.isRootProject
import com.autonomousapps.DependencyAnalysisExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class RootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        require(isRootProject) {
            "Root plugin should only be applied on the root project."
        }

        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.dependencyAnalysis.get().pluginId)
            apply(libs.plugins.gradleDoctor.get().pluginId)
            apply(libs.plugins.nodeGradle.get().pluginId)
            apply(libs.plugins.sortDependencies.get().pluginId)
            apply(libs.plugins.spotless.get().pluginId)
        }

        configure<DependencyAnalysisExtension> {
            issues {
                all {
                    ignoreKtx(true)
                    onAny {
                        severity("fail")
                    }
                    onUnusedDependencies {
                        exclude(
                            "com.google.dagger:hilt-android",
                        )
                    }
                    onUsedTransitiveDependencies {
                        severity("ignore")
                    }
                }

                project(":app") {
                    onUnusedDependencies {
                        exclude(
                            // Submodules used by Hilt
                            // https://github.com/autonomousapps/dependency-analysis-android-gradle-plugin/issues/791
                            ":core:coil",
                            ":core:okhttp",
                        )
                    }
                }
            }

            abi {
                exclusions {
                    ignoreInternalPackages()
                    ignoreGeneratedCode()
                }
            }
        }

        configure<SpotlessExtension> {
            configureSpotless()
            predeclareDeps()
        }
        configure<SpotlessExtensionPredeclare> {
            configureSpotless()
        }
    }
}
