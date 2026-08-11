package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import com.mikepenz.aboutlibraries.plugin.AboutLibrariesExtension
import com.mikepenz.aboutlibraries.plugin.StrictMode
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AboutLibrariesPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.aboutlibraries)

      configure<AboutLibrariesExtension> {
        export {
          // Everything the license settings screen does not show, to keep the asset small.
          excludeFields.set(
            setOf("License.content", "Library.description", "Library.funding", "Library.tag")
          )
        }

        library { requireLicense.set(true) }

        license {
          allowedLicenses.set(setOf("ASDKL", "Apache-2.0", "BSD-3-Clause", "MIT"))
          strictMode.set(StrictMode.FAIL)
        }
      }
    }
}
