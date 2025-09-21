package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureKotlin
import app.rickandmorty.gradle.util.isAndroidTestEnabled
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

public class AndroidLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.android.library)

      configureKotlin()
      configure<KotlinProjectExtension> { explicitApi() }

      configure<LibraryExtension> {
        configureAndroid()

        val targetSdk = libs.versions.android.sdk.target.get().toInt()
        lint.targetSdk = targetSdk
        testOptions.targetSdk = targetSdk

        buildTypes { release { isDefault = true } }
        testBuildType = "release"
      }

      configure<LibraryAndroidComponentsExtension> {
        beforeVariants { builder ->
          if (builder.buildType == "debug") {
            builder.enable = false
          }

          builder.androidTest.enable = isAndroidTestEnabled
        }
      }
    }
}
