package app.rickandmorty.plugins.licenses

import java.nio.file.Path

internal data class Coordinates(val group: String, val artifact: String, val version: String) {
  val id: String
    get() = "$group:$artifact"

  override fun toString(): String = "$group:$artifact:$version"
}

/** Kotlin Multiplatform platform artifacts, listed under their root artifact instead. */
private val platformSuffixes = listOf("-android", "-jvm", "-desktop")

/**
 * Lists the Maven dependencies on the Android runtime classpath of [moduleName].
 *
 * The plugin API only exposes JVM classpaths, so this asks the toolchain itself.
 */
internal fun resolveRuntimeDependencies(projectRoot: Path, moduleName: String): List<Coordinates> {
  val wrapper = projectRoot.resolve(if (isWindows) "kotlin.bat" else "kotlin").toString()
  val process =
    ProcessBuilder(
        wrapper,
        "show",
        "dependencies",
        "--module=$moduleName",
        "--platform-group=android",
        "--scope=runtime",
      )
      .directory(projectRoot.toFile())
      .redirectErrorStream(true)
      .start()
  val output = process.inputStream.bufferedReader().readText()
  check(process.waitFor() == 0) { "Failed to resolve the dependencies of $moduleName:\n$output" }

  val coordinates =
    output
      .lineSequence()
      .mapNotNull { line ->
        parseCoordinates(line.substringAfter("─── ", missingDelimiterValue = ""))
      }
      .toSet()

  val ids = coordinates.mapTo(HashSet()) { it.id }
  return coordinates
    .filterNot { c ->
      platformSuffixes.any { suffix ->
        c.artifact.endsWith(suffix) && "${c.group}:${c.artifact.removeSuffix(suffix)}" in ids
      }
    }
    .distinctBy { it.id }
}

/** Parses `group:artifact:version`, `group:artifact:version -> resolved`, ignoring constraints. */
private fun parseCoordinates(node: String): Coordinates? {
  if (node.isEmpty() || node.endsWith("(c)")) return null
  val versions = node.removeSuffix("(*)").trim().split(" -> ")
  val parts = versions.first().split(':')
  // Direct dependencies are also listed as `module:fragment:group:artifact:version`, and local
  // modules as paths; the plain coordinates follow on the next line.
  if (parts.size != 3 || parts.any { it.isEmpty() || '/' in it }) return null
  return Coordinates(
    group = parts[0],
    artifact = parts[1],
    version = if (versions.size > 1) versions.last().trim() else parts[2],
  )
}

private val isWindows = "windows" in System.getProperty("os.name").lowercase()
