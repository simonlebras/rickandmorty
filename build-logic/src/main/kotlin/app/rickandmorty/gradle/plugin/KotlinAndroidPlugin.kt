package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureKotlin
import app.rickandmorty.gradle.util.withPlugin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

public class KotlinAndroidPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.kotlin.android)

      configureKotlin()

      pluginManager.withPlugin(libs.plugins.android.library) {
        configure<KotlinProjectExtension> { explicitApi() }
      }
    }
}
