package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureKotlin
import com.android.build.api.dsl.TestExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AndroidTestPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.android.test)

      configureKotlin()

      configure<TestExtension> {
        configureAndroid()

        defaultConfig.targetSdk = libs.versions.android.sdk.target.get().toInt()

        buildTypes { debug { matchingFallbacks += "release" } }
      }
    }
}
