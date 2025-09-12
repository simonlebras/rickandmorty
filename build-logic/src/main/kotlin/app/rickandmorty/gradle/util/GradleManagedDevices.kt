package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.assign
import com.android.build.api.dsl.ManagedDevices
import org.gradle.api.GradleException
import org.gradle.api.Project

private val deviceConfigs =
  listOf(
    DeviceConfig(device = "Pixel 4", apiLevel = 30, systemImageSource = "aosp-atd"),
    DeviceConfig(device = "Pixel 6", apiLevel = 31, systemImageSource = "aosp"),
    DeviceConfig(device = "Pixel C", apiLevel = 30, systemImageSource = "aosp-atd"),
  )

@Suppress("UnstableApiUsage")
context(managedDevices: ManagedDevices)
internal fun Project.configureGradleManagedDevices() {
  managedDevices.localDevices.apply {
    deviceConfigs.forEach { config ->
      create(config.taskName) {
        device = config.device
        apiLevel = config.apiLevel
        systemImageSource = config.systemImageSource
      }
    }
  }
}

private data class DeviceConfig(
  val device: String,
  val apiLevel: Int,
  val systemImageSource: String,
) {
  val taskName = buildString {
    append(device.replace(" ", "").toLowerCaseAsciiOnly())
    append("api$apiLevel")
    append(
      when (systemImageSource) {
        "aosp" -> "aosp"
        "aosp-atd" -> "aospatd"
        "google" -> "google"
        "google-atd" -> "googleatd"
        "google_apis_playstore" -> "googleplaystore"
        "android-desktop" -> "desktop"
        else -> throw GradleException("Unknown system image source!")
      }
    )
  }
}

private fun String.toLowerCaseAsciiOnly(): String = buildString {
  for (c in this@toLowerCaseAsciiOnly) {
    append(if (c in 'A'..'Z') c.lowercaseChar() else c)
  }
}
