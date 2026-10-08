package app.rickandmorty.plugins.licenses

import java.nio.file.Path
import org.jetbrains.amper.plugins.Configurable

@Configurable
interface LicensesSettings {
  /** The generated AboutLibraries JSON, read at runtime as a Java resource. */
  val output: Path

  /** SPDX ids of the licenses dependencies may use. Any other license fails the build. */
  val allowedLicenses: List<String>
    get() = listOf("Apache-2.0", "ASDKL", "BSD-3-Clause", "MIT")
}
