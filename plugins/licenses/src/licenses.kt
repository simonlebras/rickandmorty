package app.rickandmorty.plugins.licenses

import java.nio.file.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText
import org.jetbrains.amper.plugins.ExecutionAvoidance
import org.jetbrains.amper.plugins.Output
import org.jetbrains.amper.plugins.TaskAction

@TaskAction(executionAvoidance = ExecutionAvoidance.Disabled)
fun checkLicenses(
  projectRoot: String,
  moduleName: String,
  allowedLicenses: List<String>,
  // Not an @Input: it is written by the update command, which this check must not depend on.
  output: String,
  @Output workDir: Path,
) {
  val actual = generateLicenses(projectRoot, moduleName, allowedLicenses, workDir)
  val outputFile = Path.of(output)
  val expected = if (outputFile.exists()) outputFile.readText() else ""
  check(actual == expected) {
    "$outputFile is outdated, run `./kotlin do updateLicenses -m $moduleName` to update it."
  }
}

@TaskAction(executionAvoidance = ExecutionAvoidance.Disabled)
fun updateLicenses(
  projectRoot: String,
  moduleName: String,
  allowedLicenses: List<String>,
  output: String,
  @Output workDir: Path,
) {
  val outputFile = Path.of(output)
  outputFile.createParentDirectories()
  outputFile.writeText(generateLicenses(projectRoot, moduleName, allowedLicenses, workDir))
  println("Updated $outputFile")
}

private fun generateLicenses(
  projectRoot: String,
  moduleName: String,
  allowedLicenses: List<String>,
  workDir: Path,
): String {
  val coordinates = resolveRuntimeDependencies(Path.of(projectRoot), moduleName)
  val pomResolver = PomResolver(cacheDir = workDir.resolve("poms"))
  val libraries = coordinates.map { pomResolver.library(it) }.sortedBy { it.uniqueId }

  val disallowed = libraries.filter { library ->
    library.licenses.isEmpty() || library.licenses.any { it.id !in allowedLicenses }
  }
  check(disallowed.isEmpty()) {
    buildString {
      appendLine("Dependencies with missing or disallowed licenses (allowed: $allowedLicenses):")
      disallowed.forEach {
        appendLine("  ${it.uniqueId}:${it.version} ${it.licenses.map { l -> l.id }}")
      }
    }
  }

  return AboutLibrariesJson.encode(libraries)
}
