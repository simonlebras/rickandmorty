package app.rickandmorty.data.database

import app.rickandmorty.data.database.dao.CharacterDao
import app.rickandmorty.data.database.dao.CharacterPagedEntryDao
import app.rickandmorty.data.database.dao.EpisodeDao
import app.rickandmorty.data.database.dao.EpisodePagedEntryDao
import app.rickandmorty.data.database.dao.LocationDao
import app.rickandmorty.data.database.dao.LocationPagedEntryDao

internal abstract class RamDatabase {
  abstract fun characterDao(): CharacterDao

  abstract fun characterPagedEntryDao(): CharacterPagedEntryDao

  abstract fun episodeDao(): EpisodeDao

  abstract fun episodePagedEntryDao(): EpisodePagedEntryDao

  abstract fun locationDao(): LocationDao

  abstract fun locationPagedEntryDao(): LocationPagedEntryDao
}
