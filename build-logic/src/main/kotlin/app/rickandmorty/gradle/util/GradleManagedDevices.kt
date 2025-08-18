@file:Suppress("InternalAgpApiUsage", "InternalGradleApiUsage")

package app.rickandmorty.gradle.util

import com.android.build.api.dsl.ManagedDevices
import com.android.build.gradle.internal.tasks.ManagedDeviceInstrumentationTestSetupTask
import com.android.build.gradle.internal.tasks.ManagedDeviceInstrumentationTestTask
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.process.ExecOperations
import org.gradle.work.DisableCachingByDefault

private val deviceConfigs =
  listOf(
    DeviceConfig(device = "Pixel 4", apiLevel = 30, systemImageSource = "aosp-atd"),
    DeviceConfig(device = "Pixel 6", apiLevel = 31, systemImageSource = "aosp"),
    DeviceConfig(device = "Pixel C", apiLevel = 30, systemImageSource = "aosp-atd"),
  )

context(managedDevices: ManagedDevices)
internal fun Project.configureGradleManagedDevices() {
  managedDevices.localDevices {
    deviceConfigs.forEach { config ->
      create(config.taskName) {
        device = config.device
        apiLevel = config.apiLevel
        systemImageSource = config.systemImageSource
      }

      // Create a limiting build service to only allow one setup task for each device configuration
      // to run at a time
      gradle.sharedServices.registerIfAbsent(
        LimitingBuildServiceGmdSetup.createKey(config),
        LimitingBuildServiceGmdSetup::class.java,
      ) {
        maxParallelUsages = 1
      }
    }
  }

  if (OperatingSystem.current().isLinux) {
    tasks.withType<ManagedDeviceInstrumentationTestTask>().whenTaskAdded {
      finalizedBy(
        tasks.register<KillEmulatorProcessesTask>("${name}KillEmulatorProcesses") {
          this.id = this@whenTaskAdded.path
        }
      )
    }
  }

  tasks.withType<ManagedDeviceInstrumentationTestSetupTask>().configureEach {
    usesService(
      gradle.sharedServices.registrations
        .getByName("LimitingBuildServiceGmdSetup${name.substringBefore("Setup")}")
        .service
    )
  }

  // Create a limiting build service to only allow an maxConcurrentDevices amount of test tasks to
  // run at a time
  val runningLimitingService =
    gradle.sharedServices.registerIfAbsent(
      LimitingBuildServiceGmdRunning.KEY,
      LimitingBuildServiceGmdRunning::class.java,
    ) {
      maxParallelUsages =
        providers
          .gradleProperty("android.experimental.testOptions.managedDevices.maxConcurrentDevices")
          .map { value -> value.toIntOrNull() ?: 1 }
    }

  tasks.withType<ManagedDeviceInstrumentationTestTask>().configureEach {
    usesService(runningLimitingService)
  }
}

private interface LimitingBuildServiceGmdRunning : BuildService<BuildServiceParameters.None> {
  companion object {
    const val KEY = "LimitingBuildServiceGmdRunning"
  }
}

private interface LimitingBuildServiceGmdSetup : BuildService<BuildServiceParameters.None> {
  companion object {
    fun createKey(config: DeviceConfig) = "LimitingBuildServiceGmdSetup${config.taskName}"
  }
}

@DisableCachingByDefault
private abstract class KillEmulatorProcessesTask : DefaultTask() {
  @get:Input abstract val id: Property<String>

  @get:Inject abstract val execOperations: ExecOperations

  @TaskAction
  fun taskAction() {
    val stream = ByteArrayOutputStream()

    execOperations.exec {
      commandLine("ps", "-ax")
      standardOutput = stream
    }

    val emulatorPidsToKill =
      stream
        .toString(Charset.forName("UTF-8"))
        .lineSequence()
        .filter { line -> line.contains(id.get()) }
        .map { it.trim().split(Regex("""\s+""")).first() }
        .toList()
    emulatorPidsToKill.forEach { pid -> execOperations.exec { commandLine("kill", "-9", pid) } }
    logger.info("Killed qemu process(es) for ${id.get()}: $emulatorPidsToKill")
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
