package app.rickandmorty.data.database.inject

import app.rickandmorty.data.database.RamDatabase
import app.rickandmorty.data.database.dao.CharacterDao
import app.rickandmorty.data.database.dao.CharacterPagedEntryDao
import app.rickandmorty.data.database.dao.EpisodeDao
import app.rickandmorty.data.database.dao.EpisodePagedEntryDao
import app.rickandmorty.data.database.dao.LocationDao
import app.rickandmorty.data.database.dao.LocationPagedEntryDao
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
public interface DaosProvider {
  public companion object {
    @Provides
    public fun provideCharacterDao(database: RamDatabase): CharacterDao = database.characterDao()

    @Provides
    public fun provideCharacterPagedEntryDao(database: RamDatabase): CharacterPagedEntryDao =
      database.characterPagedEntryDao()

    @Provides
    public fun provideEpisodeDao(database: RamDatabase): EpisodeDao = database.episodeDao()

    @Provides
    public fun provideEpisodePagedEntryDao(database: RamDatabase): EpisodePagedEntryDao =
      database.episodePagedEntryDao()

    @Provides
    public fun provideLocationDao(database: RamDatabase): LocationDao = database.locationDao()

    @Provides
    public fun provideLocationPagedEntryDao(database: RamDatabase): LocationPagedEntryDao =
      database.locationPagedEntryDao()
  }
}
