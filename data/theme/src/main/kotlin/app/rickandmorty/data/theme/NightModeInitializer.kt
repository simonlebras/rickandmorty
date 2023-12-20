package app.rickandmorty.data.theme

import app.rickandmorty.coroutines.ApplicationScope
import app.rickandmorty.startup.Initializer
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
        val nightMode = runBlocking {
            themeRepository
                .getTheme()
                .first()
                .nightMode
        }
        nightModeManager.setNightMode(nightMode)

        themeRepository.getTheme()
            .map { theme -> theme.nightMode }
            .distinctUntilChanged()
            .onEach {
                nightModeManager.setNightMode(it)
            }
            .launchIn(applicationScope)
    }
}
