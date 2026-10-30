package app.rickandmorty.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.launch

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey
class MainActivity(
  private val uiGraphFactory: UiGraph.Factory,
  private val rootContent: RootContent,
) : AppCompatActivity() {
  private val uiGraph by unsafeLazy { uiGraphFactory.create() }
  private val metroViewModelFactory by unsafeLazy { uiGraph.metroViewModelFactory }
  private val stateHolder: MainStateHolder by
    viewModels<MainViewModel>(factoryProducer = { metroViewModelFactory })

  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()

    WindowCompat.enableEdgeToEdge(window)

    super.onCreate(savedInstanceState)

    uiGraph.navigationState.attachTo(savedStateRegistry)

    lifecycleScope.launch {
      lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) { stateHolder.collect() }
    }

    splashScreen.setKeepOnScreenCondition { stateHolder.state.isLoading }

    val entryProvider = uiGraph.entryProvider
    val navigationState = uiGraph.navigationState
    val navigator = uiGraph.navigator

    setContent {
      RamTheme(useDynamicColor = stateHolder.state.useDynamicColor) {
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
}
