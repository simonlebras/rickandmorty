package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureBadgingTasks
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AndroidApplicationPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.android.application)

      configure<ApplicationExtension> {
        configureAndroid()

        defaultConfig.targetSdk = libs.versions.android.sdk.target.get().toInt()

        packaging.resources.excludes +=
          setOf(
            "**/*.pro",
            "**/*.proto",
            "**/*.properties",
            "*.properties",
            "LICENSE_*",
            "/META-INF/{AL2.0,LGPL2.1}",
            "META-INF/CHANGES",
            "META-INF/LICENSE",
            "META-INF/LICENSE.txt",
            "META-INF/NOTICE",
            "META-INF/NOTICE.txt",
          )

        buildTypes {
          debug {
            matchingFallbacks += "release"
            isDefault = true
          }
        }
      }

      configure<ApplicationAndroidComponentsExtension> {
        configureBadgingTasks()

        onVariants(selector().withBuildType("release")) { variant ->
          variant.packaging.resources.excludes.addAll(
            "**/*.kotlin_metadata",
            "DebugProbesKt.bin",
            "kotlin/**",
            "META-INF/*.kotlin_module",
            "META-INF/*.version",
            "META-INF/androidx.*",
          )
        }
      }

      tasks.named("check").configure { dependsOn("checkReleaseBadging") }
    }
}
