package app.rickandmorty.data.locale

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface PlatformLocaleManager {
  fun getAppLocale(): Flow<Locale?>

  suspend fun setAppLocale(locale: Locale?)

  suspend fun getAvailableAppLocales(): ImmutableList<Locale>
}
