package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.AndroidSdkVersions
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureJvmCompatibility
import app.rickandmorty.gradle.util.configureKotlinCompilerOptions
import app.rickandmorty.gradle.util.kotlin
import com.android.build.api.dsl.TestExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AndroidTestPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.android.test)

      kotlin { configureKotlinCompilerOptions() }

      configureJvmCompatibility()

      configure<TestExtension> {
        configureAndroid()

        defaultConfig.targetSdk = AndroidSdkVersions.TARGET_SDK

        buildTypes { debug { matchingFallbacks += "release" } }
      }
    }
}
