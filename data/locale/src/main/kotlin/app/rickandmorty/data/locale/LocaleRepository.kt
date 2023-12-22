package app.rickandmorty.data.locale

import app.rickandmorty.data.model.Locale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface LocaleRepository {
    public fun getAppLocale(): Flow<Locale?>

    public suspend fun setAppLocale(locale: Locale?)

    public suspend fun getAvailableAppLocales(): ImmutableList<Locale>
}
