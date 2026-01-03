package app.rickandmorty.data.locale

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

@ContributesBinding(AppScope::class)
public class LocaleRepositoryImpl(private val platformLocaleManager: PlatformLocaleManager) :
  LocaleRepository {

  override fun getAppLocale(): Flow<Locale?> = platformLocaleManager.getAppLocale()

  override suspend fun setAppLocale(locale: Locale?) {
    platformLocaleManager.setAppLocale(locale)
  }

  override suspend fun getAvailableAppLocales(): ImmutableList<Locale> =
    platformLocaleManager.getAvailableAppLocales()
}
