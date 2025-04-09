package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureAndroid
import com.android.build.api.dsl.TestExtension
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

public class AndroidTestPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      pluginManager.apply(libs.plugins.android.test, libs.plugins.cachefix)

      configure<TestExtension> {
        configureAndroid(this)

        defaultConfig.targetSdk = libs.versions.android.sdk.target.get().toInt()

        buildTypes { debug { matchingFallbacks += "release" } }
      }
    }
}
