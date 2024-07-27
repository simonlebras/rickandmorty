package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.configureSpotless
import app.rickandmorty.gradle.util.isRootProject
import app.rickandmorty.gradle.util.withPlugin
import com.autonomousapps.DependencyAnalysisExtension
import com.dropbox.affectedmoduledetector.AffectedModuleConfiguration
import com.osacky.doctor.DoctorExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
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

        checkRequiredJdkVersion(libs)

        with(pluginManager) {
            withPlugin(libs.plugins.affectedmoduledetector) {
                configureAffectedModuleDetector()
            }

            withPlugin(libs.plugins.dependencyanalysis) {
                configureDependencyAnalysis()
            }

            withPlugin(libs.plugins.gradledoctor) {
                configureGradleDoctor()
            }

            withPlugin(libs.plugins.spotless) {
                configureSpotless(libs)
            }
        }
    }
}

private fun checkRequiredJdkVersion(libs: LibrariesForLibs) {
    val jdkVersion = libs.versions.java.jdk.get()
    val currentJvmVersion = JavaVersion.current().majorVersion
    check(jdkVersion == currentJvmVersion) {
        "Current Java version ($currentJvmVersion) does not match the required version ($jdkVersion)."
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
        val reportsFolder = rootProject.layout
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
