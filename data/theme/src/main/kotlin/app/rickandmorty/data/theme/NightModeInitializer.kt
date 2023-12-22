package app.rickandmorty.data.theme

import app.rickandmorty.core.coroutines.ApplicationScope
import app.rickandmorty.core.startup.Initializer
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
    private val themeRepository: ThemeRepository,
    private val nightModeManager: NightModeManager,
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
        nightModeManager.setNightMode(currentNightMode)

        // Update default night mode to match the theme
        themeRepository.getTheme()
            .map { theme -> theme.nightMode }
            .distinctUntilChanged()
            .onEach { nightMode ->
                nightModeManager.setNightMode(nightMode)
            }
            .launchIn(applicationScope)
    }
}
