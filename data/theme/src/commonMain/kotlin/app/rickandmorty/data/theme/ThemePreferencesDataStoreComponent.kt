package app.rickandmorty.data.theme

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import me.tatarka.inject.annotations.Provides
import okio.FileSystem
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

private const val PREFERENCES_FILE_NAME = "theme_preferences.pb"

@ContributesTo(AppScope::class)
public interface ThemePreferencesDataStoreComponent {
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
