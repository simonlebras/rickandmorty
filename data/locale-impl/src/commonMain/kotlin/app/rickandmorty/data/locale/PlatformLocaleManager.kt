package app.rickandmorty.data.locale

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface PlatformLocaleManager {
  public fun getAppLocale(): Flow<Locale?>

  public suspend fun setAppLocale(locale: Locale?)

  public suspend fun getAvailableAppLocales(): ImmutableList<Locale>
}
