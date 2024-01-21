package app.rickandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.rickandmorty.data.database.dao.CharacterDao
import app.rickandmorty.data.database.dao.CharacterPagedEntryDao
import app.rickandmorty.data.database.dao.LocationDao
import app.rickandmorty.data.database.dao.LocationPagedEntryDao
import app.rickandmorty.data.database.entity.CharacterEntity
import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity
import app.rickandmorty.data.database.entity.LocationEntity
import app.rickandmorty.data.database.entity.LocationPagedEntryEntity
import se.ansman.dagger.auto.AutoBind
import se.ansman.dagger.auto.androidx.room.AutoProvideDaos

@Database(
    entities = [
        CharacterEntity::class,
        CharacterPagedEntryEntity::class,
        LocationEntity::class,
        LocationPagedEntryEntity::class,
    ],
    version = 1,
)
@AutoBind
@AutoProvideDaos
internal abstract class RamDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterPagedEntryDao(): CharacterPagedEntryDao
    abstract fun locationDao(): LocationDao
    abstract fun locationPagedEntryDao(): LocationPagedEntryDao
}
