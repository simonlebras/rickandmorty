package app.rickandmorty.data.license

import kotlinx.serialization.Serializable

@Serializable
public data class LicenseJson(
  val artifactId: String,
  val groupId: String,
  val version: String,
  val url: String?,
)
