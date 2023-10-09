package app.rickandmorty.theme.data

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService
import app.rickandmorty.coroutines.ApplicationScope
import app.rickandmorty.startup.Initializer
import app.rickandmorty.theme.domain.GetThemeUseCase
import app.rickandmorty.theme.domain.NightMode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import se.ansman.dagger.auto.AutoBindIntoSet

@AutoBindIntoSet
internal class NightModeInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getTheme: GetThemeUseCase,
) : Initializer {
    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    override fun initialize() {
        val uiModeManager = context.getSystemService<UiModeManager>()!!

        getTheme()
            .map { theme -> theme.nightMode }
            .onEach { nightMode ->
                AppCompatDelegate.setDefaultNightMode(nightMode.toAppCompatNightMode())

                if (Build.VERSION.SDK_INT >= 31) {
                    uiModeManager.setApplicationNightMode(nightMode.toUiModeManagerNightMode())
                }
            }
            .launchIn(applicationScope)
    }
}

@AppCompatDelegate.NightMode
private fun NightMode.toAppCompatNightMode() = when (this) {
    NightMode.AUTO_BATTERY -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
    NightMode.FOLLOW_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    NightMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
    NightMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
}

private fun NightMode.toUiModeManagerNightMode() = when (this) {
    NightMode.FOLLOW_SYSTEM -> UiModeManager.MODE_NIGHT_AUTO
    NightMode.LIGHT -> UiModeManager.MODE_NIGHT_NO
    NightMode.DARK -> UiModeManager.MODE_NIGHT_YES
    else -> throw IllegalStateException("Unsupported night mode: $this")
}
