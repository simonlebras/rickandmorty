package app.rickandmorty.theme.domain

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface ThemeRepository {
    fun getTheme(): Flow<Theme>

    suspend fun setNightMode(nightMode: NightMode)

    suspend fun setUseDynamicColor(useDynamicColor: Boolean)

    fun getAvailableNightModes(): ImmutableList<NightMode>
}
