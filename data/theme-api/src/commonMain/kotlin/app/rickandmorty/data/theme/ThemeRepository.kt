package app.rickandmorty.data.theme

import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface ThemeRepository {
  public fun getTheme(): Flow<Theme>

  public suspend fun setNightMode(nightMode: NightMode)

  public suspend fun setUseDynamicColor(useDynamicColor: Boolean)

  public fun getAvailableNightModes(): ImmutableList<NightMode>
}
