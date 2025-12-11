package app.rickandmorty.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import app.rickandmorty.core.designsystem.theme.LocalSharedTransitionScope
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.ui.location.list.LocationList

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RamApp(navEntryInstallers: Set<NavEntryInstaller>, modifier: Modifier = Modifier) {
  NavigationSuiteScaffold(navigationItems = {}, modifier = modifier) {
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
    val backStack = rememberNavBackStack(LocationList)
    NavDisplay(
      entries =
        rememberDecoratedNavEntries(
          backStack = backStack,
          entryDecorators =
            listOf(
              rememberSaveableStateHolderNavEntryDecorator(),
              rememberViewModelStoreNavEntryDecorator(),
            ),
          entryProvider =
            entryProvider {
              navEntryInstallers.forEach { entryInstaller -> with(entryInstaller) { install() } }
            },
        ),
      sceneStrategy = listDetailStrategy,
      sharedTransitionScope = LocalSharedTransitionScope.current,
      onBack = {},
    )
  }
}
