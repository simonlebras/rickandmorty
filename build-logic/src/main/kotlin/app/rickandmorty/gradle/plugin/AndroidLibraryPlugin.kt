package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.androidTestRuntimeOnly
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureKotlin
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.DeviceTestBuilder.Companion.ANDROID_TEST_TYPE
import com.android.build.api.variant.HasDeviceTestsBuilder
import com.android.build.api.variant.HasHostTestsBuilder
import com.android.build.api.variant.HostTestBuilder.Companion.SCREENSHOT_TEST_TYPE
import com.android.build.api.variant.HostTestBuilder.Companion.UNIT_TEST_TYPE
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
        beforeVariants(selector().withBuildType("debug")) { builder -> builder.enable = false }

        beforeVariants(selector().withBuildType("release")) { builder ->
          with(builder as HasHostTestsBuilder) {
            val isUnitTestEnabled =
              providers.gradleProperty("ram.android.enableUnitTest").orNull == "true"
            hostTests[UNIT_TEST_TYPE]!!.enable = isUnitTestEnabled
            hostTests[SCREENSHOT_TEST_TYPE]!!.enable = isUnitTestEnabled
          }

          with(builder as HasDeviceTestsBuilder) {
            val isDeviceTestEnabled =
              providers.gradleProperty("ram.android.enableDeviceTest").orNull == "true"
            deviceTests[ANDROID_TEST_TYPE]!!.enable = isDeviceTestEnabled

            if (isDeviceTestEnabled) {
              dependencies { androidTestRuntimeOnly(libs.androidx.test.runner) }
            }
          }
        }
      }
    }
}
