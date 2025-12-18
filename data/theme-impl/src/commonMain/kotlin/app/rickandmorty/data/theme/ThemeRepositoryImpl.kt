package app.rickandmorty.data.theme

import androidx.datastore.core.DataStore
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ContributesBinding(AppScope::class)
public class ThemeRepositoryImpl(
  private val dataStore: DataStore<ThemeProto>,
  @ApplicationScope private val applicationScope: CoroutineScope,
) : ThemeRepository {

  override fun getTheme(): Flow<Theme> =
    dataStore.data
      .map { theme ->
        Theme(nightMode = theme.nightMode.toNightMode(), useDynamicColor = theme.useDynamicColor)
      }
      .distinctUntilChanged()

  override suspend fun setNightMode(nightMode: NightMode) {
    applicationScope
      .launch {
        dataStore.updateData { theme -> theme.copy(nightMode = nightMode.toNightModeProto()) }
      }
      .join()
  }

  override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
    applicationScope
      .launch { dataStore.updateData { theme -> theme.copy(useDynamicColor = useDynamicColor) } }
      .join()
  }

  override fun getAvailableNightModes(): ImmutableList<NightMode> =
    persistentListOf(NightMode.Light, NightMode.Dark, defaultNightMode)
}

private fun NightMode.toNightModeProto() =
  when (this) {
    NightMode.AutoBattery -> NightModeProto.AUTO_BATTERY
    NightMode.FollowSystem -> NightModeProto.FOLLOW_SYSTEM
    NightMode.Light -> NightModeProto.LIGHT
    NightMode.Dark -> NightModeProto.DARK
  }

private fun NightModeProto.toNightMode() =
  when (this) {
    NightModeProto.UNSPECIFIED,
    NightModeProto.AUTO_BATTERY,
    NightModeProto.FOLLOW_SYSTEM -> defaultNightMode

    NightModeProto.LIGHT -> NightMode.Light
    NightModeProto.DARK -> NightMode.Dark
  }
