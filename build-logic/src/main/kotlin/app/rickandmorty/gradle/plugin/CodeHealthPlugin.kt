package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.isRootProject
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class CodeHealthPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.dependencyanalysis, libs.plugins.sortdependencies, libs.plugins.spotless)

      configureSpotless()
    }
}

private fun Project.configureSpotless() {
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
