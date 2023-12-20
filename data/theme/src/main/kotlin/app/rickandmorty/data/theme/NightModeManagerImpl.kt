package app.rickandmorty.data.theme

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService
import app.rickandmorty.data.model.NightMode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import se.ansman.dagger.auto.AutoBind

@AutoBind
internal class NightModeManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NightModeManager {
    override fun setNightMode(nightMode: NightMode) {
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
