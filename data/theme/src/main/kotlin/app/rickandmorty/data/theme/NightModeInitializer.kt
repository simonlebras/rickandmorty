package app.rickandmorty.data.theme

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService
import app.rickandmorty.core.coroutines.ApplicationScope
import app.rickandmorty.core.startup.Initializer
import app.rickandmorty.data.model.NightMode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import se.ansman.dagger.auto.AutoBindIntoSet

@AutoBindIntoSet
internal class NightModeInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val themeRepository: ThemeRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : Initializer {
    override fun initialize() {
        // Set default night mode during app startup
        val currentNightMode = runBlocking {
            themeRepository
                .getTheme()
                .first()
                .nightMode
        }
        setNightMode(currentNightMode)

        // Update default night mode to match the theme
        themeRepository.getTheme()
            .map { theme -> theme.nightMode }
            .distinctUntilChanged()
            .onEach { nightMode ->
                setNightMode(nightMode)
            }
            .launchIn(applicationScope)
    }

    private fun setNightMode(nightMode: NightMode) {
        if (Build.VERSION.SDK_INT >= 31) {
            val uiModeManager = context.getSystemService<UiModeManager>()!!
            uiModeManager.setApplicationNightMode(nightMode.toUiModeManagerNightMode())
        } else {
            AppCompatDelegate.setDefaultNightMode(nightMode.toAppCompatNightMode())
        }
    }
}

@AppCompatDelegate.NightMode
private fun NightMode.toAppCompatNightMode() = when (this) {
    NightMode.AutoBattery -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
    NightMode.FollowSystem -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    NightMode.Light -> AppCompatDelegate.MODE_NIGHT_NO
    NightMode.Dark -> AppCompatDelegate.MODE_NIGHT_YES
}

private fun NightMode.toUiModeManagerNightMode() = when (this) {
    NightMode.FollowSystem -> UiModeManager.MODE_NIGHT_AUTO
    NightMode.Light -> UiModeManager.MODE_NIGHT_NO
    NightMode.Dark -> UiModeManager.MODE_NIGHT_YES
    else -> throw IllegalStateException("Unsupported night mode: $this")
}
