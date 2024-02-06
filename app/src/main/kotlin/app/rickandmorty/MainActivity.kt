package app.rickandmorty

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.util.Consumer
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.metrics.performance.JankStats
import androidx.navigation.compose.rememberNavController
import app.rickandmorty.core.contentview.ContentViewSetter
import app.rickandmorty.core.designsystem.theme.RamTheme
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.data.model.NightMode
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var contentViewSetter: ContentViewSetter

    @Inject
    lateinit var jankStats: Lazy<JankStats>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        var uiState by mutableStateOf(MainUiState())

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .launchIn(this)
            }
        }

        splashScreen.setKeepOnScreenCondition {
            !uiState.isLoading
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val composeView = ComposeView(this).apply {
            setContent {
                RamContent(uiState = uiState)
            }
        }
        contentViewSetter.setContentView(this, composeView)
    }

    override fun onResume() {
        super.onResume()

        jankStats.get().isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()

        jankStats.get().isTrackingEnabled = false
    }
}

@Composable
private fun ComponentActivity.RamContent(uiState: MainUiState) {
    val darkTheme = uiState.useDarkTheme()

    DisposableEffect(darkTheme) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
                detectDarkMode = { darkTheme },
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = lightScrim,
                darkScrim = darkScrim,
                detectDarkMode = { darkTheme },
            ),
        )
        onDispose { }
    }

    val navController = rememberNavController()

    DisposableEffect(navController) {
        val listener = Consumer<Intent> {
            navController.handleDeepLink(it)
        }
        addOnNewIntentListener(listener)
        onDispose { removeOnNewIntentListener(listener) }
    }

    RamTheme(
        darkTheme = darkTheme,
        dynamicColor = uiState.useDynamicColor(),
    ) {
        RamContent(navController = navController)
    }
}

@Composable
@ReadOnlyComposable
private fun MainUiState.useDarkTheme() = when (theme) {
    is Incomplete -> isSystemInDarkTheme()
    else -> when (theme()!!.nightMode) {
        NightMode.AutoBattery,
        NightMode.FollowSystem,
        -> isSystemInDarkTheme()

        NightMode.Light -> false
        NightMode.Dark -> true
    }
}
