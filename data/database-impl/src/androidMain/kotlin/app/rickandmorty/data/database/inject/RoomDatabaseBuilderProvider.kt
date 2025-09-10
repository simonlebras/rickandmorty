import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import app.rickandmorty.core.metro.AppContext
import app.rickandmorty.data.database.DATABASE_NAME
import app.rickandmorty.data.database.RamDatabase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
public interface RoomDatabaseBuilderProvider {
  public companion object {
    @Provides
    public fun provideRoomDatabaseBuilder(
      @AppContext context: Context
    ): RoomDatabase.Builder<RamDatabase> {
      val databaseFile = context.getDatabasePath(DATABASE_NAME)
      return Room.databaseBuilder<RamDatabase>(context = context, name = databaseFile.absolutePath)
    }
  }
}
