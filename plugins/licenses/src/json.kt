package app.rickandmorty.plugins.licenses

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.add
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject

/** Encodes libraries in the format read by `com.mikepenz.aboutlibraries.Libs.Builder.withJson`. */
internal object AboutLibrariesJson {
  private val json = Json { prettyPrint = true }

  fun encode(libraries: List<Library>): String {
    val root = buildJsonObject {
      putJsonArray("libraries") {
        libraries.forEach { library -> add(library.toJson()) }
      }
      putJsonObject("licenses") {
        libraries
          .flatMap { it.licenses }
          .distinctBy { it.id }
          .sortedBy { it.id }
          .forEach { license ->
            putJsonObject(license.id) {
              put("name", license.name)
              license.url?.let { put("url", it) }
              put("internalHash", license.id)
              put("spdxId", license.id)
              put("hash", license.id)
            }
          }
      }
    }
    return json.encodeToString(JsonObject.serializer(), root) + "\n"
  }

  private fun Library.toJson(): JsonObject = buildJsonObject {
    put("uniqueId", uniqueId)
    put("artifactVersion", version)
    put("name", name)
    website?.let { put("website", it) }
    putJsonArray("developers") { developers.forEach { addJsonObject { put("name", it) } } }
    organization?.let { putJsonObject("organization") { put("name", it) } }
    if (scmConnection != null || scmUrl != null) {
      putJsonObject("scm") {
        scmConnection?.let { put("connection", it) }
        scmUrl?.let { put("url", it) }
      }
    }
    putJsonArray("licenses") { licenses.forEach { add(it.id) } }
  }
}
