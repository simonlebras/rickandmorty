package app.rickandmorty.plugins.badging

import com.android.tools.build.bundletool.androidtools.Aapt2Command
import com.android.tools.build.bundletool.commands.BuildApksCommand
import com.android.tools.build.bundletool.commands.BuildApksCommand.ApkBuildMode
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.util.zip.ZipFile
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteRecursively
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.io.path.outputStream
import kotlin.io.path.readText
import kotlin.io.path.setPosixFilePermissions
import kotlin.io.path.writeText
import org.jetbrains.amper.plugins.Classpath
import org.jetbrains.amper.plugins.ExecutionAvoidance
import org.jetbrains.amper.plugins.Input
import org.jetbrains.amper.plugins.Output
import org.jetbrains.amper.plugins.TaskAction

@TaskAction(executionAvoidance = ExecutionAvoidance.Disabled)
fun checkBadging(
  @Input bundle: Path,
  @Input golden: Path,
  @Input aapt2: Classpath,
  moduleName: String,
  @Output workDir: Path,
) {
  val actual = dumpBadging(bundle, aapt2, workDir)
  val expected = if (golden.exists()) golden.readText() else ""
  if (actual != expected) {
    val actualFile = workDir / golden.fileName.toString()
    actualFile.writeText(actual)
    error(
      buildString {
        appendLine("Badging of $bundle changed:")
        appendLine(diff(expected, actual))
        append("If this is expected, run `./kotlin do updateBadging -m $moduleName`.")
      },
    )
  }
}

@TaskAction(executionAvoidance = ExecutionAvoidance.Disabled)
fun updateBadging(
  @Input bundle: Path,
  @Input aapt2: Classpath,
  // Not an @Output: the check task reads this file and must not depend on this task.
  golden: String,
  @Output workDir: Path,
) {
  val goldenFile = Path.of(golden)
  goldenFile.writeText(dumpBadging(bundle, aapt2, workDir))
  println("Updated $goldenFile")
}

@OptIn(ExperimentalPathApi::class)
private fun dumpBadging(bundle: Path, aapt2Classpath: Classpath, workDir: Path): String {
  check(bundle.exists()) { "$bundle does not exist, run `./kotlin package` first." }

  workDir.deleteRecursively()
  workDir.createDirectories()

  val aapt2 = extractAapt2(aapt2Classpath, workDir)

  val apks = workDir / "bundle.apks"
  BuildApksCommand.builder()
    .setBundlePath(bundle)
    .setOutputFile(apks)
    .setApkBuildMode(ApkBuildMode.UNIVERSAL)
    .setAapt2Command(Aapt2Command.createFromExecutablePath(aapt2))
    .build()
    .execute()

  val universalApk = workDir / "universal.apk"
  ZipFile(apks.toFile()).use { zip ->
    zip.getInputStream(zip.getEntry("universal.apk")).use { input ->
      universalApk.outputStream().use { input.copyTo(it) }
    }
  }

  val process =
    ProcessBuilder(aapt2.toString(), "dump", "badging", universalApk.toString())
      .redirectErrorStream(true)
      .start()
  val output = process.inputStream.bufferedReader().readText()
  check(process.waitFor() == 0) { "aapt2 dump badging failed:\n$output" }
  return output
}

/** Extracts the aapt2 executable for the host OS from the `com.android.tools.build:aapt2` jars. */
private fun extractAapt2(classpath: Classpath, workDir: Path): Path {
  val os = System.getProperty("os.name").lowercase()
  val classifier =
    when {
      "mac" in os -> "osx"
      "linux" in os -> "linux"
      else -> error("Unsupported OS for aapt2: $os")
    }
  val jar =
    classpath.resolvedFiles.singleOrNull { it.fileName.toString().endsWith("-$classifier.jar") }
      ?: error("No aapt2 jar for $classifier in ${classpath.resolvedFiles}")

  val aapt2 = workDir / "aapt2"
  ZipFile(jar.toFile()).use { zip ->
    zip.getInputStream(zip.getEntry("aapt2")).use { input ->
      aapt2.outputStream().use { input.copyTo(it) }
    }
  }
  aapt2.setPosixFilePermissions(
    setOf(
      PosixFilePermission.OWNER_READ,
      PosixFilePermission.OWNER_WRITE,
      PosixFilePermission.OWNER_EXECUTE,
    ),
  )
  return aapt2
}

/**
 * A minimal line diff: lines only in [expected] are prefixed by `-`, lines only in [actual] by `+`.
 * When both contain the same lines in a different order, the first reordered line is reported.
 */
private fun diff(expected: String, actual: String): String {
  val expectedLines = expected.lines()
  val actualLines = actual.lines()
  val removed = expectedLines.filterNot { it in actualLines }.map { "- $it" }
  val added = actualLines.filterNot { it in expectedLines }.map { "+ $it" }
  if (removed.isNotEmpty() || added.isNotEmpty()) return (removed + added).joinToString("\n")

  val line = expectedLines.indices.first { expectedLines[it] != actualLines.getOrNull(it) }
  return "Lines are reordered, starting at line ${line + 1}:\n- ${expectedLines[line]}\n+ ${actualLines[line]}"
}
