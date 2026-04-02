package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.isFirebaseEnabled
import app.rickandmorty.gradle.util.withPlugin
import com.android.build.api.dsl.ApplicationExtension
import com.google.firebase.perf.plugin.FirebasePerfExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class FirebasePerfPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      if (isFirebaseEnabled) {
        val libs = the<LibrariesForLibs>()

        apply(libs.plugins.firebase.perf, libs.plugins.googleservices)

        pluginManager.withPlugin(libs.plugins.android.application) {
          configure<ApplicationExtension> {
            buildTypes {
              debug { configure<FirebasePerfExtension> { setInstrumentationEnabled(false) } }
            }
          }
        }

        dependencies {
          implementation(platform(libs.firebase.bom))
          implementation(libs.firebase.perf)
        }
      }
    }
}
