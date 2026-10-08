package app.rickandmorty.plugins.badging

import java.nio.file.Path
import org.jetbrains.amper.plugins.Classpath
import org.jetbrains.amper.plugins.Configurable

@Configurable
interface BadgingSettings {
  /** The release bundle printed by `kotlin package`. */
  val bundle: Path

  /** The golden badging file. */
  val golden: Path

  /** The aapt2 executable, as the `com.android.tools.build:aapt2` jars for each host OS. */
  val aapt2: Classpath
}
