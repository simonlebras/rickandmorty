package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.ktlint
import app.rickandmorty.gradle.util.ktlintGradle
import app.rickandmorty.gradle.util.misc
import app.rickandmorty.gradle.util.prettier
import app.rickandmorty.gradle.util.xml
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class SpotlessPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.nodeGradle.get().pluginId)
            apply(libs.plugins.spotless.get().pluginId)
        }

        configure<SpotlessExtension> {
            ktlint {
                target("src/**/*.kt")
            }

            ktlintGradle {
                target("*.kts")
            }

            misc {
                target(
                    ".editorconfig",
                    ".gitattributes",
                    ".gitignore",
                    "*.md",
                    "*.pro",
                    "*.properties",
                )
            }

            prettier {
                target(
                    "*.json",
                    "assets/**/*.json",
                    "src/**/*.json",
                    "src/**/*.graphql",
                )
            }

            xml {
                target(
                    "*.xml",
                    "src/**/*.xml",
                )
            }
        }
    }
}
