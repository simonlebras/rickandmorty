package app.rickandmorty.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEach
import app.rickandmorty.core.navigation.LocalNavigator
import org.jetbrains.compose.resources.stringResource

@Composable
fun RamAppState.NavSuite(
  modifier: Modifier = Modifier,
  layoutType: NavigationSuiteType =
    NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()),
) {
  val navigator = LocalNavigator.current
  NavigationSuite(
    colors = NavigationSuiteDefaults.colors(),
    navigationSuiteType = layoutType,
    content = {
      topLevelDestinations.fastForEach { item ->
        val isSelected = topLevelRoute == item.route
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
  )
}
