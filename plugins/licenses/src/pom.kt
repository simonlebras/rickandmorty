package app.rickandmorty.plugins.licenses

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText
import org.w3c.dom.Element

internal data class License(val id: String, val name: String, val url: String?)

internal data class Library(
  val uniqueId: String,
  val version: String,
  val name: String,
  val website: String?,
  val developers: List<String>,
  val organization: String?,
  val scmConnection: String?,
  val scmUrl: String?,
  val licenses: List<License>,
)

private val repositories =
  listOf("https://dl.google.com/dl/android/maven2", "https://repo.maven.apache.org/maven2")

/** Reads library metadata from POM files, following parent POMs like Maven does. */
internal class PomResolver(private val cacheDir: Path) {
  private val httpClient =
    HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()

  fun library(coordinates: Coordinates): Library {
    val poms = generateSequence(pom(coordinates)) { it.parent?.let(::pom) }.toList()
    val first = poms.first()
    return Library(
      uniqueId = coordinates.id,
      version = coordinates.version,
      name = first.name ?: coordinates.id,
      website = poms.firstNotNullOfOrNull { it.url },
      developers = poms.firstOrNull { it.developers.isNotEmpty() }?.developers.orEmpty(),
      organization = poms.firstNotNullOfOrNull { it.organization },
      scmConnection = poms.firstNotNullOfOrNull { it.scmConnection },
      scmUrl = poms.firstNotNullOfOrNull { it.scmUrl },
      licenses = poms.firstOrNull { it.licenses.isNotEmpty() }?.licenses.orEmpty(),
    )
  }

  private fun pom(coordinates: Coordinates): Pom = Pom.parse(coordinates, download(coordinates))

  private fun download(c: Coordinates): String {
    val path =
      "${c.group.replace('.', '/')}/${c.artifact}/${c.version}/${c.artifact}-${c.version}.pom"
    val cached = cacheDir.resolve(path)
    if (cached.exists()) return cached.readText()

    for (repository in repositories) {
      val request = HttpRequest.newBuilder(URI.create("$repository/$path")).GET().build()
      val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
      if (response.statusCode() == 200) {
        cached.createParentDirectories()
        cached.writeText(response.body())
        return response.body()
      }
    }
    error("Could not find the POM of $c in $repositories")
  }
}

private class Pom(
  val parent: Coordinates?,
  val name: String?,
  val url: String?,
  val developers: List<String>,
  val organization: String?,
  val scmConnection: String?,
  val scmUrl: String?,
  val licenses: List<License>,
) {
  companion object {
    fun parse(coordinates: Coordinates, xml: String): Pom {
      val factory = DocumentBuilderFactory.newInstance()
      factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
      val project = factory.newDocumentBuilder().parse(xml.byteInputStream()).documentElement

      val properties =
        mapOf("project.version" to coordinates.version, "version" to coordinates.version)
      fun String.interpolate(): String =
        properties.entries.fold(this) { value, (key, replacement) ->
          value.replace("\${$key}", replacement)
        }

      fun Element.text(name: String): String? =
        child(name)?.textContent?.trim()?.takeIf { it.isNotEmpty() }?.interpolate()

      return Pom(
        parent =
          project.child("parent")?.let { parent ->
            Coordinates(
              group = parent.text("groupId")!!,
              artifact = parent.text("artifactId")!!,
              version = parent.text("version")!!,
            )
          },
        name = project.text("name"),
        url = project.text("url"),
        developers =
          project.child("developers")?.children("developer").orEmpty().mapNotNull {
            it.text("name") ?: it.text("organization")
          },
        organization = project.child("organization")?.text("name"),
        scmConnection = project.child("scm")?.text("connection"),
        scmUrl = project.child("scm")?.text("url"),
        licenses =
          project.child("licenses")?.children("license").orEmpty().map {
            normalizeLicense(name = it.text("name").orEmpty(), url = it.text("url"))
          },
      )
    }
  }
}

private fun Element.children(name: String): List<Element> =
  (0 until childNodes.length)
    .map { childNodes.item(it) }
    .filterIsInstance<Element>()
    .filter { it.tagName == name }

private fun Element.child(name: String): Element? = children(name).firstOrNull()

/** Maps POM license names and URLs to SPDX ids. */
private fun normalizeLicense(name: String, url: String?): License {
  val text = "$name $url".lowercase()
  return when {
    "apache" in text && ("2.0" in text || "license-2.0" in text) ->
      License("Apache-2.0", "Apache License 2.0", "https://spdx.org/licenses/Apache-2.0.html")
    "mit" in text.split(Regex("[^a-z]")) || "opensource.org/licenses/mit" in text ->
      License("MIT", "MIT License", "https://spdx.org/licenses/MIT.html")
    "bsd" in text && ("3" in text || "new" in text || "revised" in text) ->
      License(
        "BSD-3-Clause",
        "BSD 3-Clause \"New\" or \"Revised\" License",
        "https://spdx.org/licenses/BSD-3-Clause.html",
      )
    "android software development kit license" in text ||
      "developer.android.com/studio/terms" in text ->
      License(
        "ASDKL",
        "Android Software Development Kit License",
        "https://developer.android.com/studio/terms.html",
      )
    else -> License(id = name.ifEmpty { url ?: "unknown" }, name = name, url = url)
  }
}
