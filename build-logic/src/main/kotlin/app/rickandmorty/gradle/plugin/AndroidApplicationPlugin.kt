package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureBadgingTasks
import app.rickandmorty.gradle.util.configureKotlin
import app.rickandmorty.gradle.util.getOrCreateTask
import app.rickandmorty.gradle.util.isAndroidTestEnabled
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.HasUnitTestBuilder
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class AndroidApplicationPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.android.application)

      configureKotlin()

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

        beforeVariants { builder ->
          if (builder.buildType == "release") {
            (builder as HasUnitTestBuilder).enableUnitTest = false
          }

          builder.androidTest.enable = isAndroidTestEnabled
        }

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

      getOrCreateTask<Task>("check") { dependsOn("checkReleaseBadging") }
    }
}
