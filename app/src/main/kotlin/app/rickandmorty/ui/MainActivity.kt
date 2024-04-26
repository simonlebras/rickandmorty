package app.rickandmorty.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.metrics.performance.JankStats
import app.rickandmorty.core.designsystem.theme.RamTheme
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var jankStats: Lazy<JankStats>

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        enableEdgeToEdge()

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

        setContent {
            val darkTheme = isSystemInDarkTheme()

            LaunchedEffect(darkTheme) {
                enableEdgeToEdge()
            }

            RamTheme(
                darkTheme = darkTheme,
                dynamicColor = uiState.useDynamicColor,
            ) {
                RamApp()
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
