package app.rickandmorty.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEach
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import app.rickandmorty.core.designsystem.theme.LocalSharedTransitionScope
import app.rickandmorty.core.navigation.LocalNavigator
import org.jetbrains.compose.resources.stringResource

@Composable
fun RamAppState.AdaptiveNavigationSuiteScaffold(
  modifier: Modifier = Modifier,
  navigationItems: @Composable () -> Unit = {
    val navigator = LocalNavigator.current
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
  primaryActionContent: @Composable () -> Unit = {},
  content: @Composable () -> Unit,
) {
  AnimatedNavigationSuiteScaffold(
    visibilityScope = LocalNavAnimatedContentScope.current,
    transitionScope = LocalSharedTransitionScope.current,
    navigationItems = navigationItems,
    modifier = modifier,
    navigationItemVerticalArrangement = Arrangement.Top,
    primaryActionContent = primaryActionContent,
    content = content,
  )
}
