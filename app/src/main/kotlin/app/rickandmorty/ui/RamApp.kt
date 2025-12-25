package app.rickandmorty.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEach
import androidx.navigation3.ui.NavDisplay
import app.rickandmorty.core.designsystem.theme.LocalSharedTransitionScope
import app.rickandmorty.core.navigation.LocalNavigator
import app.rickandmorty.core.navigation.Navigator
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RamApp(appState: RamAppState, modifier: Modifier = Modifier) {
  val navigator = remember(appState.navigationState) { Navigator(appState.navigationState) }

  CompositionLocalProvider(LocalNavigator provides navigator) {
    NavigationSuiteScaffold(
      navigationItems = {
        appState.topLevelDestinations.fastForEach { item ->
          val isSelected = appState.topLevelRoute == item.route
          NavigationSuiteItem(
            selected = isSelected,
            onClick = { navigator.navigate(item.route) },
            icon = {
              Icon(
                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                contentDescription = null,
              )
            },
            label = { Text(stringResource(item.label)) },
          )
        }
      },
      modifier = modifier,
    ) {
      NavDisplay(
        entries = appState.currentEntries,
        sceneStrategy = rememberListDetailSceneStrategy(),
        sharedTransitionScope = LocalSharedTransitionScope.current,
        onBack = navigator::goBack,
      )
    }
  }
}
