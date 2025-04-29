package app.rickandmorty.gradle.util

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless() {
  val spotlessFormatters: SpotlessExtension.() -> Unit = {
    val ktfmtVersion = libs.versions.ktfmt.get()

    kotlin {
      ktfmt(ktfmtVersion).googleStyle().configure { it.setRemoveUnusedImports(true) }
      target("src/**/*.kt")
    }

    kotlinGradle {
      ktfmt(ktfmtVersion).googleStyle().configure { it.setRemoveUnusedImports(true) }
      target("*.kts")
    }

    json {
      gson().indentWithSpaces(2)
      target("assets/**/*.json", "src/**/*.json", "*.json")
    }

    format("misc") {
      target(".gitattributes", ".gitignore", "*.md", "*.pro", "*.properties")
      leadingTabsToSpaces(2)
      trimTrailingWhitespace()
      endWithNewline()
    }

    format("xml") {
      target("src/**/*.xml", "*.xml")
      leadingTabsToSpaces(2)
      trimTrailingWhitespace()
      endWithNewline()
    }
  }

  configure<SpotlessExtension> {
    spotlessFormatters()
    if (isRootProject) {
      predeclareDeps()
    }
  }
  if (isRootProject) {
    configure<SpotlessExtensionPredeclare> { spotlessFormatters() }
  }
}
