package app.rickandmorty.data.theme

import androidx.compose.runtime.State
import kotlinx.collections.immutable.ImmutableList

public interface ThemeRepository {
  public val theme: State<Theme?>

  public fun setNightMode(nightMode: NightMode)

  public fun setUseDynamicColor(useDynamicColor: Boolean)

  public fun getAvailableNightModes(): ImmutableList<NightMode>
}
