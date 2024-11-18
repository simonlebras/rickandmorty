package app.rickandmorty.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.rickandmorty.core.designsystem.theme.RamTheme
import app.rickandmorty.core.ui.isSystemInDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        setupEdgeToEdge()

        super.onCreate(savedInstanceState)

        var uiState by mutableStateOf(MainUiState())

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .launchIn(this)

                isSystemInDarkTheme()
                    .onEach { setupEdgeToEdge() }
                    .launchIn(this)
            }
        }

        splashScreen.setKeepOnScreenCondition {
            uiState.isLoading
        }

        setContent {
            RamTheme(dynamicColor = uiState.useDynamicColor) {
                RamApp()
            }
        }
    }

    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= 29) {
            window.isNavigationBarContrastEnforced = false
        }
    }
}
