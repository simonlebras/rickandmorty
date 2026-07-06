package app.rickandmorty.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.core.designsystem.theme.RamTheme
import app.rickandmorty.core.rootcontent.RootContent
import app.rickandmorty.inject.UiGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.android.ActivityKey
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import kotlin.getValue
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey
class MainActivity(
  private val uiGraphFactory: UiGraph.Factory,
  private val rootContent: RootContent,
) : AppCompatActivity() {
  init {
    // https://issuetracker.google.com/issues/139738913
    if (Build.VERSION.SDK_INT == 29 && isTaskRoot) {
      onBackPressedDispatcher.addCallback { finishAfterTransition() }
    }
  }

  private val uiGraphHolder by
    viewModels<UiGraphHolder> {
      viewModelFactory {
        initializer { UiGraphHolder(uiGraphFactory.create(), createSavedStateHandle()) }
      }
    }
  private val uiGraph
    get() = uiGraphHolder.graph

  private val metroViewModelFactory by unsafeLazy { uiGraph.metroViewModelFactory }
  private val viewModel by viewModels<MainViewModel>(factoryProducer = { metroViewModelFactory })

  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()

    setupEdgeToEdge()

    super.onCreate(savedInstanceState)

    var uiState by mutableStateOf(MainUiState())

    lifecycleScope.launch {
      lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.onEach { uiState = it }.launchIn(this)
      }
    }

    splashScreen.setKeepOnScreenCondition { uiState.isLoading }

    val entryProvider = uiGraph.entryProvider
    val navigationState = uiGraph.navigationState
    val navigator = uiGraph.navigator

    setContent {
      RamTheme(useDynamicColor = uiState.useDynamicColor) {
        val customTabUriHandler = rememberCustomTabsUriHandler()

        CompositionLocalProvider(
          LocalMetroViewModelFactory provides metroViewModelFactory,
          LocalUriHandler provides customTabUriHandler,
        ) {
          rootContent.Content {
            val appState =
              rememberRamAppState(
                entryProvider = entryProvider,
                navigationState = navigationState,
              )

            RamApp(
              appState = appState,
              onTopLevelRouteClick = { navigator.navigate(it) },
              onBack = navigator::goBack,
              modifier = Modifier.semantics { testTagsAsResourceId = true },
            )
          }
        }
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
