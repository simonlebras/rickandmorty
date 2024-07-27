package app.rickandmorty.data.license

import kotlinx.serialization.Serializable

@Serializable
public data class LicenseJson(
  val groupId: String,
  val artifactId: String,
  val version: String,
  val spdxLicenses: List<SpdxLicenseJson>?,
  val name: String?,
  val scm: ScmJson?,
)

@Serializable
public data class SpdxLicenseJson(val identifier: String, val name: String, val url: String)

@Serializable public data class ScmJson(val url: String)
