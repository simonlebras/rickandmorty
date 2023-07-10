package app.rickandmorty

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.util.Consumer
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.metrics.performance.JankStats
import androidx.navigation.compose.rememberNavController
import app.rickandmorty.designsystem.theme.RamTheme
import app.rickandmorty.hilt.HiltLazy
import com.google.accompanist.adaptive.calculateDisplayFeatures
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var jankStats: HiltLazy<JankStats>

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

        setContent {
            // Update the system bars to match the theme
            val darkMode = isSystemInDarkTheme()
            DisposableEffect(darkMode) {
                enableEdgeToEdge()
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

            RamTheme(dynamicColor = uiState.useDynamicColor()) {
                val windowSizeClass = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this).toImmutableList()
                RamApp(
                    navController = navController,
                    windowSizeClass = windowSizeClass,
                    displayFeatures = displayFeatures,
                )
            }
        }
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
