package app.rickandmorty.data.locale

import app.rickandmorty.core.coroutines.inject.ApplicationScope
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ContributesBinding(AppScope::class)
public class LocaleRepositoryImpl(
  private val platformLocaleManager: PlatformLocaleManager,
  @ApplicationScope private val applicationScope: CoroutineScope,
) : LocaleRepository {
  override fun getAppLocale(): Flow<Locale?> {
    return platformLocaleManager.getAppLocale()
  }

  override suspend fun setAppLocale(locale: Locale?) {
    applicationScope.launch { platformLocaleManager.setAppLocale(locale) }.join()
  }

  override suspend fun getAvailableAppLocales(): ImmutableList<Locale> {
    return platformLocaleManager.getAvailableAppLocales()
  }
}
