package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.isRootProject
import app.rickandmorty.gradle.util.ktlint
import app.rickandmorty.gradle.util.ktlintGradle
import app.rickandmorty.gradle.util.misc
import app.rickandmorty.gradle.util.prettier
import app.rickandmorty.gradle.util.xml
import com.autonomousapps.DependencyAnalysisExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.diffplug.spotless.LineEnding
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
                            ":core:jankstats",
                            ":core:okhttp",
                            ":core:strictmode",
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
            // https://github.com/diffplug/spotless/issues/1644
            lineEndings = LineEnding.PLATFORM_NATIVE

            ktlint {
                target("build-logic/src/**/*.kt")
            }

            ktlintGradle {
                target(
                    "*.kts",
                    "build-logic/*.kts",
                )
            }

            misc {
                target(
                    ".editorconfig",
                    ".gitattributes",
                    ".gitignore",
                    "*.md",
                    "*.properties",
                    "gradle/libs.versions.toml",
                )
            }

            prettier {
                target(
                    "*.json",
                    ".github/**/*.yml",
                )
            }

            xml {
                target("*.xml")
            }

            predeclareDeps()
        }
        configure<SpotlessExtensionPredeclare> {
            ktlint { }
            prettier { }
        }
    }
}
