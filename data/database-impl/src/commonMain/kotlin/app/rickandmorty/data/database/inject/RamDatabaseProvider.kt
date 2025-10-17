package app.rickandmorty.data.database.inject

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.data.database.RamDatabase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlin.coroutines.CoroutineContext

@ContributesTo(AppScope::class)
public interface RamDatabaseProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideRamDatabase(
      builder: RoomDatabase.Builder<RamDatabase>,
      @IODispatcher ioDispatcher: CoroutineContext,
    ): RamDatabase =
      builder.setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(ioDispatcher).build()
  }
}
