package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.assign
import app.rickandmorty.gradle.dsl.register
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.google.common.truth.Truth.assertWithMessage
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.process.ExecOperations

@CacheableTask
private abstract class GenerateBadgingTask : DefaultTask() {
  @get:OutputFile abstract val badging: RegularFileProperty

  @get:PathSensitive(PathSensitivity.NONE) @get:InputFile abstract val apk: RegularFileProperty

  @get:PathSensitive(PathSensitivity.NONE)
  @get:InputFile
  abstract val aapt2Executable: RegularFileProperty

  @get:Inject abstract val execOperations: ExecOperations

  @TaskAction
  fun taskAction() {
    execOperations.exec {
      commandLine(
        aapt2Executable.get().asFile.absolutePath,
        "dump",
        "badging",
        apk.get().asFile.absolutePath,
      )
      standardOutput = badging.asFile.get().outputStream()
    }
  }
}

@CacheableTask
internal abstract class CheckBadgingTask : DefaultTask() {
  // In order for the task to be up-to-date when the inputs have not changed,
  // the task must declare an output, even if it's not used. Tasks with no
  // output are always run regardless of whether the inputs changed
  @get:OutputDirectory abstract val output: DirectoryProperty

  @get:PathSensitive(PathSensitivity.NONE)
  @get:InputFile
  abstract val goldenBadging: RegularFileProperty

  @get:PathSensitive(PathSensitivity.NONE)
  @get:InputFile
  abstract val generatedBadging: RegularFileProperty

  @get:Input abstract val updateBadgingTaskName: Property<String>

  override fun getGroup(): String = LifecycleBasePlugin.VERIFICATION_GROUP

  @TaskAction
  fun taskAction() {
    assertWithMessage(
        "Generated badging is different from golden badging! " +
          "If this change is intended, run ./gradlew ${updateBadgingTaskName.get()}"
      )
      .that(generatedBadging.get().asFile.readText())
      .isEqualTo(goldenBadging.get().asFile.readText())
  }
}

@Suppress("UnstableApiUsage")
context(componentsExtension: ApplicationAndroidComponentsExtension)
internal fun Project.configureBadgingTasks() {
  componentsExtension.onVariants { variant ->
    val capitalizedVariantName = variant.name.capitalize()

    val generateBadgingTaskName = "generate${capitalizedVariantName}Badging"
    val generateBadging =
      tasks.register<GenerateBadgingTask>(generateBadgingTaskName) {
        apk = variant.artifacts.get(SingleArtifact.APK_FROM_BUNDLE)
        aapt2Executable = componentsExtension.sdkComponents.aapt2.flatMap { it.executable }
        badging =
          layout.buildDirectory.file(
            "outputs/apk_from_bundle/${variant.name}/${variant.name}-badging.txt"
          )
      }

    val updateBadgingTaskName = "update${capitalizedVariantName}Badging"
    tasks.register<Copy>(updateBadgingTaskName) {
      from(generateBadging.map { it.badging })
      into(layout.projectDirectory)
    }

    val checkBadgingTaskName = "check${capitalizedVariantName}Badging"
    tasks.register<CheckBadgingTask>(checkBadgingTaskName) {
      goldenBadging = layout.projectDirectory.file("${variant.name}-badging.txt")
      generatedBadging = generateBadging.flatMap { it.badging }
      this.updateBadgingTaskName = updateBadgingTaskName
      output = layout.buildDirectory.dir("intermediates/$checkBadgingTaskName")
    }
  }
}
