package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.isRootProject
import app.rickandmorty.gradle.util.withPlugin
import com.autonomousapps.DependencyAnalysisExtension
import com.dropbox.affectedmoduledetector.AffectedModuleConfiguration
import com.osacky.doctor.DoctorExtension
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure

public class RootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        require(isRootProject) {
            "Root plugin should only be applied on the root project."
        }

        configureAffectedModuleDetector()

        configureDependencyAnalysis()

        configureGradleDoctor()
    }
}

private fun Project.configureAffectedModuleDetector() {
    pluginManager.withPlugin(libs.plugins.affectedmoduledetector) {
        configure<AffectedModuleConfiguration> {
            baseDir = "${project.rootDir}"
            pathsAffectingAllModules = setOf(
                "build-logic",
                "gradle/libs.versions.toml",
            )
            excludedModules = setOf(
                "baselineprofile",
            )

            logFilename = "output.log"
            val reportsFolder = project.layout
                .buildDirectory
                .dir("reports/affectedModuleDetector")
                .get()
            logFolder = "$reportsFolder"

            val baseRef = providers.gradleProperty("affectedBaseRef").orNull
            if (!baseRef.isNullOrEmpty()) {
                specifiedBranch = baseRef.replace("refs/heads/", "")
                compareFrom = "SpecifiedBranchCommit"
            } else {
                compareFrom = "PreviousCommit"
            }

            customTasks = setOf(
                AffectedModuleConfiguration.CustomTask(
                    "runAffectedScreenshotTests",
                    "validateDebugScreenshotTest",
                    "Runs all affected screenshot tests.",
                ),
            )
        }
    }
}

private fun Project.configureDependencyAnalysis() {
    pluginManager.withPlugin(libs.plugins.dependencyanalysis) {
        configure<DependencyAnalysisExtension> {
            issues {
                all {
                    onAny {
                        severity("fail")
                    }
                    onIncorrectConfiguration {
                        exclude("org.jetbrains.kotlin:kotlin-stdlib")
                    }
                    onModuleStructure {
                        exclude("android")
                    }
                    onUsedTransitiveDependencies {
                        severity("ignore")
                    }
                }
            }

            abi {
                exclusions {
                    ignoreGeneratedCode()
                }
            }
        }
    }
}

private fun Project.configureGradleDoctor() {
    pluginManager.withPlugin(libs.plugins.gradledoctor) {
        configure<DoctorExtension> {
            warnWhenNotUsingParallelGC = false
        }
    }
}
