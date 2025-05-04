package app.rickandmorty.data.theme

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import okio.FileSystem

private const val PREFERENCES_FILE_NAME = "theme_preferences.pb"

@ContributesTo(AppScope::class)
public interface ThemePreferencesDataStoreProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideThemePreferencesDataStore(
      fileSystem: FileSystem,
      filePathProducer: FilePathProducer,
      serializer: ThemePreferencesSerializer,
    ): ThemeDataStore =
      ThemeDataStore(
        DataStoreFactory.create(
          storage =
            OkioStorage(
              fileSystem = fileSystem,
              producePath = { filePathProducer.produceFilePath(PREFERENCES_FILE_NAME) },
              serializer = serializer,
            )
        )
      )
  }
}
