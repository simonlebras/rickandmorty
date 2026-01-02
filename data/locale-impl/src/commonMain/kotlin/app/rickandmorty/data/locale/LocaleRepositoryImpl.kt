package app.rickandmorty.data.locale

import androidx.datastore.core.DataStore
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import app.rickandmorty.core.coroutines.inject.IODispatcher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.coroutines.CoroutineContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ContributesBinding(AppScope::class)
public class LocaleRepositoryImpl(
  private val platformLocaleManager: PlatformLocaleManager,
  private val dataStore: DataStore<LocaleProto>,
  @ApplicationScope private val applicationScope: CoroutineScope,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : LocaleRepository {
  override fun getAppLocale(): Flow<Locale?> {
    return platformLocaleManager.getAppLocale()
  }

  override suspend fun setAppLocale(locale: Locale?) {
    applicationScope
      .launch {
        dataStore.updateData { preferences ->
          preferences.copy(appLocale = locale?.toLanguageTag())
        }
      }
      .join()
  }

  override suspend fun getAvailableAppLocales(): ImmutableList<Locale> {
    return platformLocaleManager.getAvailableAppLocales()
  }
}
