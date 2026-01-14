package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
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
  content: @Composable (PaddingValues) -> Unit,
) {
  Surface(color = containerColor, contentColor = contentColor) {
    NestedNavigationSuiteScaffoldLayout(
      navigationSuite = navigationSuite,
      navigationSuiteType = navigationSuiteType,
    ) { contentPadding ->
      CompositionLocalProvider(LocalScaffoldContentPadding provides contentPadding) {
        content(contentPadding)
      }
    }
  }
}

@Composable
private fun NestedNavigationSuiteScaffoldLayout(
  navigationSuite: @Composable () -> Unit,
  navigationSuiteType: NavigationSuiteType,
  content: @Composable (PaddingValues) -> Unit,
) {
  val contentPadding = remember {
    object : PaddingValues {
      var paddingHolder by mutableStateOf(PaddingValues.Zero)

      override fun calculateLeftPadding(layoutDirection: LayoutDirection) =
        paddingHolder.calculateLeftPadding(layoutDirection)

      override fun calculateTopPadding() = paddingHolder.calculateTopPadding()

      override fun calculateRightPadding(layoutDirection: LayoutDirection) =
        paddingHolder.calculateRightPadding(layoutDirection)

      override fun calculateBottomPadding() = paddingHolder.calculateBottomPadding()
    }
  }

  val navigationContent: @Composable () -> Unit =
    remember(navigationSuite) { { Box { navigationSuite() } } }
  val bodyContent: @Composable () -> Unit =
    remember(content, contentPadding) { { Box { content(contentPadding) } } }

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

    contentPadding.paddingHolder =
      if (isNavigationBar) {
        PaddingValues(bottom = navigationPlaceable.height.toDp())
      } else {
        PaddingValues(start = navigationPlaceable.width.toDp())
      }

    val contentPlaceable =
      subcompose(slotId = NavigationSuiteScaffoldLayoutContent.Content, content = bodyContent)
        .first()
        .measure(constraints)

    layout(layoutWidth, layoutHeight) {
      // The content is placed at the origin and fills the entire layout.
      contentPlaceable.placeRelative(x = 0, y = 0)

      // The navigation component is placed above the content.
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
