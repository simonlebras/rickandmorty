package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@OptIn(ExperimentalLayoutApi::class)
@Composable
public fun NestedNavigationSuiteScaffold(
  navigationSuite: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  navigationSuiteType: NavigationSuiteType =
    NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()),
  containerColor: Color = NavigationSuiteScaffoldDefaults.containerColor,
  contentColor: Color = NavigationSuiteScaffoldDefaults.contentColor,
  content: @Composable () -> Unit,
) {
  Surface(modifier = modifier, color = containerColor, contentColor = contentColor) {
    NestedNavigationSuiteScaffoldLayout(
      navigationSuite = navigationSuite,
      navigationSuiteType = navigationSuiteType,
    ) {
      content()
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun NestedNavigationSuiteScaffoldLayout(
  navigationSuite: @Composable () -> Unit,
  navigationSuiteType: NavigationSuiteType,
  content: @Composable () -> Unit,
) {
  val upstreamInsets = LocalScaffoldInsets.current
  val contentInsets = remember { MutableWindowInsets() }

  val navigationContent: @Composable () -> Unit =
    remember(navigationSuite) { { Box { navigationSuite() } } }
  val bodyContent: @Composable () -> Unit =
    remember(content, contentInsets) {
      { Box { CompositionLocalProvider(LocalScaffoldInsets provides contentInsets) { content() } } }
    }

  val layoutDirection = LocalLayoutDirection.current

  SubcomposeLayout { constraints ->
    val layoutWidth = constraints.maxWidth
    val layoutHeight = constraints.maxHeight
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    val isNavigationBar = navigationSuiteType.isNavigationBar

    val navigationPlaceable =
      subcompose(
          slotId = NavigationSuiteScaffoldLayoutContent.Navigation,
          content = navigationContent,
        )
        .first()
        .measure(looseConstraints)

    val navigationSuiteInsets =
      if (isNavigationBar) {
        WindowInsets(bottom = navigationPlaceable.height)
      } else {
        if (layoutDirection == LayoutDirection.Ltr) {
          WindowInsets(left = navigationPlaceable.width)
        } else {
          WindowInsets(right = navigationPlaceable.width)
        }
      }
    contentInsets.insets = upstreamInsets.add(navigationSuiteInsets)

    val contentPlaceable =
      subcompose(slotId = NavigationSuiteScaffoldLayoutContent.Content, content = bodyContent)
        .first()
        .measure(constraints)

    layout(layoutWidth, layoutHeight) {
      contentPlaceable.placeRelative(x = 0, y = 0)

      if (isNavigationBar) {
        navigationPlaceable.placeRelative(x = 0, y = layoutHeight - navigationPlaceable.height)
      } else {
        navigationPlaceable.placeRelative(x = 0, y = 0)
      }
    }
  }
}

private enum class NavigationSuiteScaffoldLayoutContent {
  Navigation,
  Content,
}
