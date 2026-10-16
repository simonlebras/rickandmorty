package app.rickandmorty.data.license

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
public data class License(
  val uniqueId: String,
  val name: String,
  val author: String?,
  val version: String,
  val spdxIds: ImmutableList<String>,
  val url: String?,
)
