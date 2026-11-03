package app.rickandmorty.data.theme

import androidx.appstate.statestore.StateStore
import androidx.appstate.statestore.StateStoreKey
import androidx.compose.runtime.State
import app.rickandmorty.core.appstate.PersistToDataStoreCompat
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

private val defaultTheme = Theme(nightMode = NightMode.FollowSystem, useDynamicColor = false)

@Serializable
private data object ThemeKey : StateStoreKey<Theme?>(defaultValue = null), PersistToDataStoreCompat

@ContributesBinding(AppScope::class)
internal class ThemeRepositoryImpl(
  private val stateStore: StateStore,
) : ThemeRepository {

  override val theme: State<Theme?> = stateStore.getState(ThemeKey)

  override fun setNightMode(nightMode: NightMode) {
    stateStore.updateState(ThemeKey) { current ->
      (current ?: defaultTheme).copy(nightMode = nightMode)
    }
  }

  override fun setUseDynamicColor(useDynamicColor: Boolean) {
    stateStore.updateState(ThemeKey) { current ->
      (current ?: defaultTheme).copy(useDynamicColor = useDynamicColor)
    }
  }

  override fun getAvailableNightModes(): ImmutableList<NightMode> =
    persistentListOf(NightMode.Light, NightMode.Dark, NightMode.FollowSystem)
}
