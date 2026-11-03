package app.rickandmorty.data.locale

import androidx.compose.runtime.State
import kotlinx.collections.immutable.ImmutableList

public interface LocaleRepository {
  public val appLocale: State<AppLocale>

  public fun setAppLocale(appLocale: AppLocale)

  public suspend fun getAvailableAppLocales(): ImmutableList<AppLocale>
}
