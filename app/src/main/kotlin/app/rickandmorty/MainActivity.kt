package app.rickandmorty

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.metrics.performance.JankStats
import app.rickandmorty.designsystem.theme.RamTheme
import app.rickandmorty.hilt.HiltLazy
import com.google.accompanist.adaptive.calculateDisplayFeatures
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var jankStats: HiltLazy<JankStats>

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Update the system bars to match the theme
            val darkMode = isSystemInDarkTheme()
            DisposableEffect(darkMode) {
                enableEdgeToEdge()
                onDispose { }
            }

            RamTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this).toImmutableList()
                RamApp(
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
