package app.rickandmorty.theme.data

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService
import androidx.startup.Initializer
import app.rickandmorty.coroutines.ApplicationScope
import app.rickandmorty.theme.domain.GetThemeUseCase
import app.rickandmorty.theme.domain.NightMode
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class NightModeInitializer : Initializer<Unit> {
    @Inject
    lateinit var getTheme: GetThemeUseCase

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    override fun create(context: Context) {
        EntryPointAccessors
            .fromApplication<NightModeInitializerEntryPoint>(context)
            .inject(this)

        val uiModeManager = context.getSystemService<UiModeManager>()

        getTheme()
            .map { theme -> theme.nightMode }
            .onEach { nightMode ->
                AppCompatDelegate.setDefaultNightMode(nightMode.toAppCompatNightMode())

                if (Build.VERSION.SDK_INT >= 31) {
                    uiModeManager?.setApplicationNightMode(nightMode.toUiModeManagerNightMode())
                }
            }
            .launchIn(applicationScope)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
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

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface NightModeInitializerEntryPoint {
    fun inject(initializer: NightModeInitializer)
}
