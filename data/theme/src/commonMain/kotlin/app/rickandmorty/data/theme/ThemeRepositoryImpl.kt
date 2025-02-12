package app.rickandmorty.data.theme

import app.rickandmorty.core.coroutines.inject.ApplicationScope
import app.rickandmorty.data.model.NightMode
import app.rickandmorty.data.model.Theme
import app.rickandmorty.data.theme.proto.NightMode as ProtoNightMode
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(AppScope::class)
public class ThemeRepositoryImpl(
    themeDataStore: ThemeDataStore,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : ThemeRepository {
    private val dataStore = themeDataStore.value

    override fun getTheme(): Flow<Theme> = dataStore.data
        .map { preferences ->
            Theme(
                nightMode = preferences.night_mode.toNightMode(),
                useDynamicColor = preferences.use_dynamic_color,
            )
        }
        .distinctUntilChanged()

    override suspend fun setNightMode(nightMode: NightMode) {
        applicationScope.launch {
            dataStore.updateData { preferences ->
                preferences.copy(night_mode = nightMode.toProtoNightMode())
            }
        }.join()
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        applicationScope.launch {
            dataStore.updateData { preferences ->
                preferences.copy(use_dynamic_color = useDynamicColor)
            }
        }.join()
    }

    override fun getAvailableNightModes(): ImmutableList<NightMode> = persistentListOf(
        NightMode.Light,
        NightMode.Dark,
        defaultNightMode,
    )
}

private fun NightMode.toProtoNightMode() = when (this) {
    NightMode.AutoBattery -> ProtoNightMode.AUTO_BATTERY
    NightMode.FollowSystem -> ProtoNightMode.FOLLOW_SYSTEM
    NightMode.Light -> ProtoNightMode.LIGHT
    NightMode.Dark -> ProtoNightMode.DARK
}

private fun ProtoNightMode.toNightMode() = when (this) {
    ProtoNightMode.UNSPECIFIED,
    ProtoNightMode.AUTO_BATTERY,
    ProtoNightMode.FOLLOW_SYSTEM,
    -> defaultNightMode

    ProtoNightMode.LIGHT -> NightMode.Light
    ProtoNightMode.DARK -> NightMode.Dark
}
