package app.rickandmorty.data.theme

import android.app.Application
import android.app.UiModeManager
import android.content.ComponentName
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import app.rickandmorty.core.base.allowThreadDiskReads
import app.rickandmorty.core.base.doOnActivityPreCreated
import app.rickandmorty.core.base.isComponentEnabled
import app.rickandmorty.core.base.setComponentEnabled
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.processlifecycle.inject.ProcessLifecycleOwner
import app.rickandmorty.core.startup.Initializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

@ContributesIntoSet(AppScope::class)
internal class NightModeInitializer(
  private val application: Application,
  private val themeRepository: ThemeRepository,
  @ProcessLifecycleOwner private val processLifecycleOwner: LifecycleOwner,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : Initializer {
  private val uiModeManager by unsafeLazy { application.getSystemService<UiModeManager>()!! }

  private val processLifecycleScope: CoroutineScope
    get() = processLifecycleOwner.lifecycleScope

  override fun initialize() {
    initializeNightMode()

    observeNightModeUpdates()
  }

  private fun initializeNightMode() {
    val nightModeComponent = ComponentName(application, NightModeService::class.java)
    if (!application.isComponentEnabled(nightModeComponent)) {
      val nightModeDeferred = getNightModeDeferred().also { it.start() }
      application.doOnActivityPreCreated {
        val nightMode = allowThreadDiskReads {
          runBlocking {
            nightModeDeferred.await()
          }
        }

        setApplicationNightMode(nightMode)

        application.setComponentEnabled(nightModeComponent, true)
      }
    }
  }

  private fun getNightModeDeferred(): Deferred<NightMode> =
    processLifecycleScope.async(context = ioDispatcher, start = CoroutineStart.LAZY) {
      themeRepository.getTheme().first().nightMode
    }

  private fun observeNightModeUpdates() {
    themeRepository
      .getTheme()
      .map { theme -> theme.nightMode }
      .distinctUntilChanged()
      .onEach { nightMode -> setApplicationNightMode(nightMode) }
      .flowWithLifecycle(
        lifecycle = processLifecycleOwner.lifecycle,
        minActiveState = Lifecycle.State.STARTED,
      )
      .launchIn(processLifecycleScope)
  }

  private fun setApplicationNightMode(nightMode: NightMode) {
    uiModeManager.setApplicationNightMode(nightMode.toUiModeManagerNightMode())
  }
}

private fun NightMode.toUiModeManagerNightMode() =
  when (this) {
    NightMode.FollowSystem -> UiModeManager.MODE_NIGHT_AUTO
    NightMode.Light -> UiModeManager.MODE_NIGHT_NO
    NightMode.Dark -> UiModeManager.MODE_NIGHT_YES
  }
