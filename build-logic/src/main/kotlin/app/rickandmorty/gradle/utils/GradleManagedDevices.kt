package app.rickandmorty.gradle.util

import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.dsl.TestOptions
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke

private data class GradleManagedDeviceConfig(
    val deviceName: String,
    val apiLevel: Int,
    val systemImageSource: String,
)

context(Project)
internal fun TestOptions.configureGradleManagedDevices() {
    managedDevices.devices {
        val deviceNames = listOf(
            "Nexus 4",
            "Nexus 5",
            "Pixel C",
            "Pixel 2",
            "Pixel 3 XL",
            "Pixel 6 Pro",
            "Medium Desktop",
        )
        val apiLevels = 24..34
        val systemImageSources = listOf(
            "aosp",
            "aosp-atd",
            "google",
            "google-atd",
            "google_apis_playstore",
            "android-desktop",
        )

        deviceNames.flatMap { deviceName ->
            apiLevels.flatMap { apiLevel ->
                systemImageSources.map { systemImageSource ->
                    GradleManagedDeviceConfig(
                        deviceName = deviceName,
                        apiLevel = apiLevel,
                        systemImageSource = systemImageSource,
                    )
                }
            }
        }
            .filterNot {
                // ATD is only supported on some versions
                "atd" in it.systemImageSource && it.apiLevel !in 30..33
            }
            .filterNot {
                // AOSP images are only supported on some versions
                it.systemImageSource == "aosp" && it.apiLevel > 31
            }
            .filterNot {
                // Desktop images only make sense on desktop devices
                (it.systemImageSource == "android-desktop" && "Desktop" !in it.deviceName) ||
                    (it.systemImageSource != "android-desktop" && "Desktop" in it.deviceName) ||
                    // Desktop images are only supported on some versions
                    (it.systemImageSource == "android-desktop" && it.apiLevel != 32)
            }
            .forEach { config ->
                create<ManagedVirtualDevice>(
                    buildString {
                        append(
                            config.deviceName
                                .replace(" ", "")
                                .toLowerCaseAsciiOnly(),
                        )
                        append("api${config.apiLevel}")
                        append(
                            when (config.systemImageSource) {
                                "aosp" -> "aosp"
                                "aosp-atd" -> "aospatd"
                                "google" -> "google"
                                "google-atd" -> "googleatd"
                                "google_apis_playstore" -> "googleplaystore"
                                "android-desktop" -> "desktop"
                                else -> throw GradleException("Unknown system image source!")
                            },
                        )
                    },
                ) {
                    this.device = config.deviceName
                    this.apiLevel = config.apiLevel
                    this.systemImageSource = config.systemImageSource
                }
            }
    }
}

private fun String.toLowerCaseAsciiOnly(): String = buildString {
    for (c in this@toLowerCaseAsciiOnly) {
        append(if (c in 'A'..'Z') c.lowercaseChar() else c)
    }
}
