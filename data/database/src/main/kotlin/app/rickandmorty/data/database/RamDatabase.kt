package app.rickandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.rickandmorty.data.database.dao.CharacterDao
import app.rickandmorty.data.database.dao.CharacterPagedEntryDao
import app.rickandmorty.data.database.entity.CharacterEntity
import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity
import se.ansman.dagger.auto.AutoBind
import se.ansman.dagger.auto.androidx.room.AutoProvideDaos

@Database(
    entities = [
        CharacterEntity::class,
        CharacterPagedEntryEntity::class,
    ],
    version = 1,
)
@AutoBind
@AutoProvideDaos
internal abstract class RamDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterPagedEntryDao(): CharacterPagedEntryDao
}
