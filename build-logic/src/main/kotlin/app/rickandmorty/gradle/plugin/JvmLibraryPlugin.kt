package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureKotlin
import app.rickandmorty.gradle.util.configureLint
import app.rickandmorty.gradle.util.kotlinJvm
import com.android.build.api.dsl.Lint
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class JvmLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply("java-library")
      apply(libs.plugins.android.lint, libs.plugins.kotlin.jvm)

      kotlinJvm { explicitApi() }

      configureKotlin()

      configure<Lint> { configureLint() }
    }
}
