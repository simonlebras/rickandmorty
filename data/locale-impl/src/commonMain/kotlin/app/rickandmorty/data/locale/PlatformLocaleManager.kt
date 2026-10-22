package app.rickandmorty.data.locale

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface PlatformLocaleManager {
  fun getAppLocale(): Flow<AppLocale>

  suspend fun setAppLocale(appLocale: AppLocale)

  suspend fun getAvailableAppLocales(): ImmutableList<AppLocale.Specific>
}
