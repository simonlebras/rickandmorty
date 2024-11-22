package app.rickandmorty.gradle.util

import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.dsl.TestOptions
import org.gradle.api.GradleException
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke

private val deviceConfigs = listOf(
    DeviceConfig(
        device = "Pixel 4",
        apiLevel = 30,
        systemImageSource = "aosp-atd",
    ),
    DeviceConfig(
        device = "Pixel 6",
        apiLevel = 31,
        systemImageSource = "aosp",
    ),
    DeviceConfig(
        device = "Pixel C",
        apiLevel = 30,
        systemImageSource = "aosp-atd",
    ),
)

internal fun TestOptions.configureGradleManagedDevices() {
    managedDevices {
        allDevices {
            deviceConfigs.forEach { config ->
                create<ManagedVirtualDevice>(config.taskName) {
                    device = config.device
                    apiLevel = config.apiLevel
                    systemImageSource = config.systemImageSource
                }
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
        append(
            device
                .replace(" ", "")
                .toLowerCaseAsciiOnly(),
        )
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
            },
        )
    }
}

private fun String.toLowerCaseAsciiOnly(): String = buildString {
    for (c in this@toLowerCaseAsciiOnly) {
        append(if (c in 'A'..'Z') c.lowercaseChar() else c)
    }
}
