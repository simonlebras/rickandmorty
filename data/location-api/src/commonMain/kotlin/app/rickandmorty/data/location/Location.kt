package app.rickandmorty.data.location

import androidx.compose.runtime.Immutable

@Immutable
public data class Location(
  val id: String,
  val name: String,
  val type: String,
  val dimension: String,
)
