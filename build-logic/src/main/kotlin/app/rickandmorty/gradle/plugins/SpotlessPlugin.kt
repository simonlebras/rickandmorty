package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.ktlint
import app.rickandmorty.gradle.utils.ktlintGradle
import app.rickandmorty.gradle.utils.misc
import app.rickandmorty.gradle.utils.prettier
import app.rickandmorty.gradle.utils.xml
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.spotless.LineEnding
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
            // https://github.com/diffplug/spotless/issues/1644
            lineEndings = LineEnding.PLATFORM_NATIVE

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
