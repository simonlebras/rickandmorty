package app.rickandmorty.data.license

import kotlinx.collections.immutable.ImmutableList

public data class License(
  val uniqueId: String,
  val name: String,
  val author: String?,
  val version: String,
  val spdxIds: ImmutableList<String>,
  val url: String?,
)
