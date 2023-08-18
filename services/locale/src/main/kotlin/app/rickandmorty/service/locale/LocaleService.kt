package app.rickandmorty.service.locale

import app.rickandmorty.service.locale.model.Locale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface LocaleService {
    public fun getApplicationLocale(): Flow<Locale?>

    public suspend fun setApplicationLocale(locale: Locale?)

    public suspend fun getAvailableApplicationLocales(): ImmutableList<Locale>
}
