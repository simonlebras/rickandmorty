import androidx.room.Room
import androidx.room.RoomDatabase
import app.rickandmorty.data.database.DATABASE_NAME
import app.rickandmorty.data.database.RamDatabase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@ContributesTo(AppScope::class)
public interface RoomDatabaseBuilderProvider {
  public companion object {
    @Provides
    public fun provideRoomDatabaseBuilder(): RoomDatabase.Builder<RamDatabase> {
      return Room.databaseBuilder<RamDatabase>(name = getDatabaseFilePath())
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun getDatabaseFilePath(): String {
      val documentDirectory =
        NSFileManager.defaultManager.URLForDirectory(
          directory = NSDocumentDirectory,
          inDomain = NSUserDomainMask,
          appropriateForURL = null,
          create = false,
          error = null,
        )
      return "${documentDirectory!!.path}/$DATABASE_NAME"
    }
  }
}
