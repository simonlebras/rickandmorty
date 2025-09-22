package app.rickandmorty.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import app.rickandmorty.data.database.dao.CharacterDao
import app.rickandmorty.data.database.dao.CharacterPagedEntryDao
import app.rickandmorty.data.database.dao.EpisodeDao
import app.rickandmorty.data.database.dao.EpisodePagedEntryDao
import app.rickandmorty.data.database.dao.LocationDao
import app.rickandmorty.data.database.dao.LocationPagedEntryDao
import app.rickandmorty.data.database.entity.CharacterEntity
import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity
import app.rickandmorty.data.database.entity.EpisodeEntity
import app.rickandmorty.data.database.entity.EpisodePagedEntryEntity
import app.rickandmorty.data.database.entity.LocationEntity
import app.rickandmorty.data.database.entity.LocationPagedEntryEntity

internal const val DATABASE_NAME = "ram-database.db"

@Database(
  entities =
    [
      CharacterEntity::class,
      CharacterPagedEntryEntity::class,
      EpisodeEntity::class,
      EpisodePagedEntryEntity::class,
      LocationEntity::class,
      LocationPagedEntryEntity::class,
    ],
  version = 1,
)
@ConstructedBy(RamDatabaseConstructor::class)
public abstract class RamDatabase : RoomDatabase() {
  public abstract fun characterDao(): CharacterDao

  public abstract fun characterPagedEntryDao(): CharacterPagedEntryDao

  public abstract fun episodeDao(): EpisodeDao

  public abstract fun episodePagedEntryDao(): EpisodePagedEntryDao

  public abstract fun locationDao(): LocationDao

  public abstract fun locationPagedEntryDao(): LocationPagedEntryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object RamDatabaseConstructor : RoomDatabaseConstructor<RamDatabase> {
  override fun initialize(): RamDatabase
}
