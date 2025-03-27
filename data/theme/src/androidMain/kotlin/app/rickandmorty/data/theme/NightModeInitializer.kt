package app.rickandmorty.data.theme

import android.app.Application
import android.app.UiModeManager
import android.content.ComponentName
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService
import app.rickandmorty.core.base.doOnActivityPreCreated
import app.rickandmorty.core.base.isComponentEnabled
import app.rickandmorty.core.base.setComponentEnabled
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.core.coroutines.awaitBlocking
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.startup.Initializer
import app.rickandmorty.data.model.NightMode
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
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(scope = AppScope::class, multibinding = true)
public class NightModeInitializer(
    application: Application,
    private val themeRepository: ThemeRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
    @IODispatcher private val ioDispatcher: CoroutineContext,
) : Initializer {
    private val nightModeImpl = if (Build.VERSION.SDK_INT >= 31) {
        NightMode31Impl(application)
    } else {
        NightMode24Impl(application)
    }

    override fun initialize() {
        val nightModeDeferred = getNightModeDeferred()
        nightModeImpl.initializeNightMode(nightModeDeferred)

        observeNightModeUpdates()
    }

    private fun getNightModeDeferred(): Deferred<NightMode> = applicationScope.async(
        context = ioDispatcher,
        start = CoroutineStart.LAZY,
    ) {
        themeRepository
            .getTheme()
            .first()
            .nightMode
    }

    private fun observeNightModeUpdates() {
        themeRepository.getTheme()
            .map { theme -> theme.nightMode }
            .distinctUntilChanged()
            .onEach { nightMode ->
                nightModeImpl.setApplicationNightMode(nightMode)
            }
            .launchIn(applicationScope)
    }
}

private interface NightModeImpl {
    fun initializeNightMode(nightModeDeferred: Deferred<NightMode>)

    fun setApplicationNightMode(nightMode: NightMode)
}

@RequiresApi(31)
private class NightMode31Impl(private val application: Application) : NightModeImpl {
    private val uiModeManager by unsafeLazy {
        application.getSystemService<UiModeManager>()!!
    }

    override fun initializeNightMode(nightModeDeferred: Deferred<NightMode>) {
        val nightModeComponent = ComponentName(application, NightModeService::class.java)
        if (!application.isComponentEnabled(nightModeComponent)) {
            nightModeDeferred.start()
            application.doOnActivityPreCreated {
                val nightMode = nightModeDeferred.awaitBlocking()
                setApplicationNightMode(nightMode)

                application.setComponentEnabled(nightModeComponent, true)
            }
        }
    }

    override fun setApplicationNightMode(nightMode: NightMode) {
        uiModeManager.setApplicationNightMode(nightMode.toUiModeManagerNightMode())
    }

    private fun NightMode.toUiModeManagerNightMode() = when (this) {
        NightMode.FollowSystem -> UiModeManager.MODE_NIGHT_AUTO
        NightMode.Light -> UiModeManager.MODE_NIGHT_NO
        NightMode.Dark -> UiModeManager.MODE_NIGHT_YES
        else -> throw IllegalArgumentException("Unsupported night mode: $this")
    }
}

private class NightMode24Impl(private val application: Application) : NightModeImpl {
    override fun initializeNightMode(nightModeDeferred: Deferred<NightMode>) {
        nightModeDeferred.start()
        application.doOnActivityPreCreated {
            val nightMode = nightModeDeferred.awaitBlocking()
            setApplicationNightMode(nightMode)
        }
    }

    override fun setApplicationNightMode(nightMode: NightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode.toAppCompatNightMode())
    }

    @AppCompatDelegate.NightMode
    private fun NightMode.toAppCompatNightMode() = when (this) {
        NightMode.AutoBattery -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        NightMode.FollowSystem -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        NightMode.Light -> AppCompatDelegate.MODE_NIGHT_NO
        NightMode.Dark -> AppCompatDelegate.MODE_NIGHT_YES
    }
}
