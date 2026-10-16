package app.rickandmorty.data.episode

import androidx.compose.runtime.Immutable

@Immutable
public data class Episode(
  val id: String,
  val name: String,
  val airDate: String,
  val episode: String,
)
