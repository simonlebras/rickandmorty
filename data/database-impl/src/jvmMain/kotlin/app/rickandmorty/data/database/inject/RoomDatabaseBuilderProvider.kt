import androidx.room.Room
import androidx.room.RoomDatabase
import app.rickandmorty.data.database.DATABASE_NAME
import app.rickandmorty.data.database.RamDatabase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import okio.FileSystem

@ContributesTo(AppScope::class)
public interface RoomDatabaseBuilderProvider {
  public companion object {
    @Provides
    public fun provideRoomDatabaseBuilder(): RoomDatabase.Builder<RamDatabase> {
      return Room.databaseBuilder<RamDatabase>(name = getDatabaseFilePath())
    }

    private fun getDatabaseFilePath(): String {
      return FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve(DATABASE_NAME).name
    }
  }
}
