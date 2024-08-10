package app.rickandmorty.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "ram-database.db"

@Module
@InstallIn(SingletonComponent::class)
internal object RamDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): RamDatabase = Room
        .databaseBuilder<RamDatabase>(
            context = context,
            name = DATABASE_NAME,
        )
        .build()
}
