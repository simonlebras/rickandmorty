package app.rickandmorty.data.license

import kotlinx.serialization.Serializable

@Serializable
internal data class LicenseItem(
    val groupId: String,
    val artifactId: String,
    val version: String,
    val spdxLicenses: List<SpdxLicense>?,
    val name: String?,
    val scm: Scm?,
)

@Serializable
internal data class SpdxLicense(
    val identifier: String,
    val name: String,
    val url: String,
)

@Serializable
internal data class Scm(
    val url: String,
)
