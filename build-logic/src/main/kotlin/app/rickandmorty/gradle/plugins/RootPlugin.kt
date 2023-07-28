package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.isRootProject
import app.rickandmorty.gradle.utils.ktlint
import app.rickandmorty.gradle.utils.ktlintGradle
import app.rickandmorty.gradle.utils.misc
import app.rickandmorty.gradle.utils.prettier
import app.rickandmorty.gradle.utils.withPlugin
import app.rickandmorty.gradle.utils.xml
import com.autonomousapps.DependencyAnalysisExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.diffplug.spotless.LineEnding
import com.dropbox.affectedmoduledetector.AffectedModuleConfiguration
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

        pluginManager.withPlugin(libs.plugins.affectedmoduledetector) {
            configureAffectedModuleDetector()
        }

        pluginManager.withPlugin(libs.plugins.dependencyAnalysis) {
            configureDependencyAnalysis()
        }

        pluginManager.withPlugin(libs.plugins.spotless) {
            configureSpotless()
        }
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

        val baseRef = findProperty("affected_base_ref") as? String
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
                ignoreKtx(true)
                onAny {
                    severity("fail")
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

private fun Project.configureSpotless() {
    configure<SpotlessExtension> {
        // https://github.com/diffplug/spotless/issues/1644
        lineEndings = LineEnding.PLATFORM_NATIVE

        ktlintGradle {
            target("*.kts")
        }

        misc {
            target(
                ".editorconfig",
                ".gitattributes",
                ".gitignore",
                "*.md",
                "*.properties",
                "proguard/*.pro",
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
        ktlint()
        prettier()
    }
}
