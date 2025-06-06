package app.rickandmorty.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.rickandmorty.core.designsystem.theme.RamTheme
import app.rickandmorty.core.metro.ActivityKey
import app.rickandmorty.core.ui.isSystemInDarkTheme
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Inject
@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey(MainActivity::class)
class MainActivity(private val viewModelFactory: ViewModelProvider.Factory) : AppCompatActivity() {
  init {
    // https://issuetracker.google.com/issues/139738913
    if (Build.VERSION.SDK_INT == 29 && isTaskRoot) {
      onBackPressedDispatcher.addCallback { finishAfterTransition() }
    }
  }

  private val viewModel: MainViewModel by viewModels()

  override val defaultViewModelProviderFactory: ViewModelProvider.Factory
    get() = viewModelFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()

    setupEdgeToEdge()

    super.onCreate(savedInstanceState)

    var uiState by mutableStateOf(MainUiState())

    lifecycleScope.launch {
      lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.onEach { uiState = it }.launchIn(this)

        isSystemInDarkTheme().onEach { setupEdgeToEdge() }.launchIn(this)
      }
    }

    splashScreen.setKeepOnScreenCondition { uiState.isLoading }

    setContent {
      RamTheme(useDynamicColor = uiState.useDynamicColor) {
        RamApp(modifier = Modifier.semantics { testTagsAsResourceId = true })
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
