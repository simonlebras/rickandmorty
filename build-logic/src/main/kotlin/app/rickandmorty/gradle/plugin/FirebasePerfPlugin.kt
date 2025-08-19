package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.implementation
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class FirebasePerfPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.googleservices, libs.plugins.firebase.perf)

      dependencies {
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.perf)
      }
    }
}
