package app.rickandmorty.data.locale

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface LocaleRepository {
  public fun getAppLocale(): Flow<AppLocale>

  public suspend fun setAppLocale(appLocale: AppLocale)

  public suspend fun getAvailableAppLocales(): ImmutableList<AppLocale>
}
