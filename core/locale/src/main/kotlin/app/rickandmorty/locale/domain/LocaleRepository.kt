package app.rickandmorty.locale.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface LocaleRepository {
    fun getApplicationLocale(): Flow<Locale?>

    fun setApplicationLocale(locale: Locale?)

    suspend fun getSupportedLocales(): ImmutableList<Locale>
}
