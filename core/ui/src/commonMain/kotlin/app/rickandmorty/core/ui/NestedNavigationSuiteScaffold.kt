package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy

@Composable
public fun NestedNavigationSuiteScaffold(
  navigationSuite: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  navigationSuiteType: NavigationSuiteType =
    NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfoV2()),
  containerColor: Color = NavigationSuiteScaffoldDefaults.containerColor,
  contentColor: Color = NavigationSuiteScaffoldDefaults.contentColor,
  content: @Composable () -> Unit,
) {
  Surface(modifier = modifier, color = containerColor, contentColor = contentColor) {
    NestedNavigationSuiteScaffoldLayout(
      navigationSuite = navigationSuite,
      navigationSuiteType = navigationSuiteType,
      content = content,
    )
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

  SubcomposeLayout { constraints ->
    val layoutWidth = constraints.maxWidth
    val layoutHeight = constraints.maxHeight
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    val isNavigationBar = navigationSuiteType.isNavigationBar

    val navigationPlaceables =
      subcompose(NavigationSuiteScaffoldLayoutContent.Navigation, navigationSuite).fastMap {
        it.measure(looseConstraints)
      }

    val navigationWidth = navigationPlaceables.fastMaxBy { it.width }?.width ?: 0
    val navigationHeight = navigationPlaceables.fastMaxBy { it.height }?.height ?: 0

    val navigationSuiteInsets =
      if (isNavigationBar) {
        WindowInsets(bottom = navigationHeight)
      } else {
        if (layoutDirection == LayoutDirection.Ltr) {
          WindowInsets(left = navigationWidth)
        } else {
          WindowInsets(right = navigationWidth)
        }
      }
    contentInsets.insets = upstreamInsets.add(navigationSuiteInsets)

    val contentPlaceables =
      subcompose(NavigationSuiteScaffoldLayoutContent.Content) {
          CompositionLocalProvider(LocalScaffoldInsets provides contentInsets) { content() }
        }
        .fastMap { it.measure(looseConstraints) }

    layout(layoutWidth, layoutHeight) {
      contentPlaceables.fastForEach { it.place(0, 0, zIndex = ContentZIndex) }

      if (isNavigationBar) {
        navigationPlaceables.fastForEach {
          it.place(0, layoutHeight - navigationHeight, zIndex = NavigationSuiteZIndex)
        }
      } else {
        navigationPlaceables.fastForEach { it.placeRelative(0, 0, zIndex = NavigationSuiteZIndex) }
      }
    }
  }
}

private const val ContentZIndex = 0f
private const val NavigationSuiteZIndex = 1f

private enum class NavigationSuiteScaffoldLayoutContent {
  Navigation,
  Content,
}
