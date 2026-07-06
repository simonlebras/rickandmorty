package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.register
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.capitalize
import app.rickandmorty.gradle.util.withPlugin
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

private const val LICENSES_ASSET_PATH = "licenses.json"

/**
 * Applies and configures [Licensee](https://github.com/cashapp/licensee), then packages a
 * per-variant `licenses.json` asset consumed by the license settings screen. The asset path is
 * exposed to app code as `BuildConfig.LICENSES_ASSET_PATH`.
 */
public class LicenseePlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.licensee)

      pluginManager.withPlugin(libs.plugins.android.application) {
        configure<ApplicationExtension> {
          buildFeatures.buildConfig = true

          defaultConfig.buildConfigField(
            "String",
            "LICENSES_ASSET_PATH",
            "\"$LICENSES_ASSET_PATH\"",
          )
        }

        configure<ApplicationAndroidComponentsExtension> {
          onVariants { variant ->
            val capitalizedVariantName = variant.name.capitalize()
            val generateLicensesAsset =
              tasks.register<GenerateLicensesAssetTask>(
                "generate${capitalizedVariantName}LicensesAsset"
              ) {
                artifactsJson.set(
                  layout.buildDirectory.file(
                    "reports/licensee/android$capitalizedVariantName/artifacts.json"
                  )
                )
                assetPath.set(LICENSES_ASSET_PATH)

                dependsOn("licenseeAndroid$capitalizedVariantName")
              }
            variant.sources.assets?.addGeneratedSourceDirectory(
              generateLicensesAsset,
              GenerateLicensesAssetTask::outputDirectory,
            )
          }
        }
      }
    }
}

/**
 * Slims the licensee report down to what the license settings screen needs. The url points to the
 * license itself (spdx first, then licensee's unknown licenses), falling back to the artifact's scm
 * page when no license url is reported.
 */
@CacheableTask
private abstract class GenerateLicensesAssetTask : DefaultTask() {
  @get:PathSensitive(PathSensitivity.NONE)
  @get:InputFile
  abstract val artifactsJson: RegularFileProperty

  @get:Input abstract val assetPath: Property<String>

  @get:OutputDirectory abstract val outputDirectory: DirectoryProperty

  @TaskAction
  fun generate() {
    val artifacts =
      json.decodeFromString<List<LicenseeArtifact>>(artifactsJson.get().asFile.readText())

    val licenses =
      artifacts
        .map { artifact ->
          License(
            artifactId = artifact.artifactId,
            groupId = artifact.groupId,
            version = artifact.version,
            url =
              artifact.spdxLicenses.firstOrNull()?.url
                ?: artifact.unknownLicenses.firstOrNull()?.url
                ?: artifact.scm?.url?.let(::normalizeScmUrl),
          )
        }
        .sortedWith(compareBy(License::groupId, License::artifactId))

    val outputFile = outputDirectory.get().file(assetPath.get()).asFile
    outputFile.parentFile.mkdirs()
    outputFile.writeText(json.encodeToString(licenses))
  }

  /**
   * Scm urls come in various shapes (`git@github.com:owner/repo.git`,
   * `scm:git:git://github.com/owner/repo.git`, …); GitHub ones are normalized to a plain https url,
   * others are kept only when already browsable.
   */
  private fun normalizeScmUrl(url: String): String? {
    if ("github.com" !in url) return url.takeIf { it.startsWith("http") }

    val parts =
      url
        .substringAfter("github.com")
        .removePrefix("/")
        .removePrefix(":")
        .removeSuffix(".git")
        .removeSuffix("/")
        .split("/")
    val owner = parts.getOrNull(0)?.takeIf { it.isNotBlank() } ?: return null
    val name = parts.getOrNull(1)?.takeIf { it.isNotBlank() } ?: return null
    return "https://github.com/$owner/$name"
  }
}

private val json = Json { ignoreUnknownKeys = true }

/** The subset of licensee's `artifacts.json` report consumed by [GenerateLicensesAssetTask]. */
@Serializable
private data class LicenseeArtifact(
  val artifactId: String,
  val groupId: String,
  val version: String,
  val spdxLicenses: List<LicenseeLicense> = emptyList(),
  val unknownLicenses: List<LicenseeLicense> = emptyList(),
  val scm: LicenseeScm? = null,
)

@Serializable private data class LicenseeLicense(val url: String? = null)

@Serializable private data class LicenseeScm(val url: String? = null)

@Serializable
private data class License(
  val artifactId: String,
  val groupId: String,
  val version: String,
  val url: String?,
)
