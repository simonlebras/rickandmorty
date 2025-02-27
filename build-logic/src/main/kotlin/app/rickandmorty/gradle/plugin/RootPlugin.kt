package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.isRootProject
import com.autonomousapps.DependencyAnalysisExtension
import com.dropbox.affectedmoduledetector.AffectedModuleConfiguration
import com.osacky.doctor.DoctorExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class RootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        require(isRootProject) {
            "Root plugin should only be applied on the root project."
        }

        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.rickandmorty.spotless,

            libs.plugins.affectedmoduledetector,
            libs.plugins.dependencyanalysis,
            libs.plugins.gradledoctor,
        )

        configureAffectedModuleDetector()

        configureDependencyAnalysis()

        configureGradleDoctor()
    }
}

private fun Project.configureAffectedModuleDetector() {
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
    }
}

private fun Project.configureDependencyAnalysis() {
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

private fun Project.configureGradleDoctor() {
    configure<DoctorExtension> {
        warnWhenNotUsingParallelGC = false
    }
}
