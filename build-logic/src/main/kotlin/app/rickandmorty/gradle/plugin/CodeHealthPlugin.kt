package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import com.ncorti.ktfmt.gradle.KtfmtExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class CodeHealthPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.dependencyanalysis, libs.plugins.ktfmt, libs.plugins.sortdependencies)

      configure<KtfmtExtension> { googleStyle() }
    }
}
