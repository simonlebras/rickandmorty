package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.isFirebaseEnabled
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class FirebaseCrashlyticsPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      if (isFirebaseEnabled) {
        val libs = the<LibrariesForLibs>()

        apply(libs.plugins.firebase.crashlytics, libs.plugins.googleservices)

        dependencies {
          implementation(platform(libs.firebase.bom))
          implementation(libs.firebase.crashlytics)
        }
      }
    }
}
