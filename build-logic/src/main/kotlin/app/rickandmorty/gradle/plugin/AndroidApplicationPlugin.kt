package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.androidTestRuntimeOnly
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureBadgingTasks
import app.rickandmorty.gradle.util.configureKotlin
import app.rickandmorty.gradle.util.getOrCreateTask
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.DeviceTestBuilder.Companion.ANDROID_TEST_TYPE
import com.android.build.api.variant.HasDeviceTestsBuilder
import com.android.build.api.variant.HasHostTestsBuilder
import com.android.build.api.variant.HostTestBuilder.Companion.SCREENSHOT_TEST_TYPE
import com.android.build.api.variant.HostTestBuilder.Companion.UNIT_TEST_TYPE
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

        @Suppress("UnstableApiUsage")
        beforeVariants() { builder ->
          with(builder as HasHostTestsBuilder) {
            val isUnitTestEnabled =
              builder.buildType != "release" &&
                providers.gradleProperty("ram.android.enableUnitTest").orNull == "true"
            hostTests[UNIT_TEST_TYPE]!!.enable = isUnitTestEnabled
            hostTests[SCREENSHOT_TEST_TYPE]!!.enable = isUnitTestEnabled
          }

          with(builder as HasDeviceTestsBuilder) {
            val isDeviceTestEnabled =
              builder.buildType != "release" &&
                providers.gradleProperty("ram.android.enableDeviceTest").orNull == "true"
            deviceTests[ANDROID_TEST_TYPE]!!.enable = isDeviceTestEnabled

            if (isDeviceTestEnabled) {
              dependencies { androidTestRuntimeOnly(libs.androidx.test.runner) }
            }
          }
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
