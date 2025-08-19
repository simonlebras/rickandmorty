package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

internal fun Project.configureSpotless() {
  val spotlessFormatters: SpotlessExtension.() -> Unit = {
    val libs = the<LibrariesForLibs>()
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
