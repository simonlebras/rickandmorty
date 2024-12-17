package app.rickandmorty.data.database

import android.content.Context
import androidx.room.Room

private const val DATABASE_NAME = "ram-database.db"

internal object RamDatabaseModule {
    fun provideAppDatabase(context: Context): RamDatabase = Room
        .databaseBuilder<RamDatabase>(
            context = context,
            name = DATABASE_NAME,
        )
        .build()
}
