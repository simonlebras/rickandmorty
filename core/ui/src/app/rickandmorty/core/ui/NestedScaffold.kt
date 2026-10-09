package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneScaffoldPaneScope
import androidx.compose.material3.adaptive.navigation3.LocalListDetailSceneScope
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
public fun NestedScaffold(
  modifier: Modifier = Modifier,
  topBar: (@Composable () -> Unit)? = null,
  bottomBar: (@Composable () -> Unit)? = null,
  snackbarHost: @Composable () -> Unit = {},
  floatingActionButton: @Composable () -> Unit = {},
  floatingActionButtonPosition: FabPosition = FabPosition.End,
  containerColor: Color = MaterialTheme.colorScheme.background,
  contentColor: Color = contentColorFor(containerColor),
  contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
  content: @Composable (PaddingValues) -> Unit,
) {
  val isStartEdgePane =
    LocalListDetailSceneScope.current?.let { scope ->
      val paneRole = (scope.scaffoldTransitionScope as? PaneScaffoldPaneScope<*>)?.paneRole
      paneRole == null || paneRole == ListDetailPaneScaffoldRole.List
    } ?: true

  val upstreamInsets = LocalScaffoldInsets.current

  val mergedInsets = remember { MutableWindowInsets() }
  val appliedUpstreamInsets =
    if (isStartEdgePane) {
      upstreamInsets
    } else {
      upstreamInsets.only(WindowInsetsSides.Vertical)
    }
  mergedInsets.insets = contentWindowInsets.add(appliedUpstreamInsets)

  val barInsets = remember { MutableWindowInsets() }
  barInsets.insets =
    if (isStartEdgePane) {
      upstreamInsets.only(WindowInsetsSides.Start)
    } else {
      WindowInsets()
    }

  Scaffold(
    modifier = modifier,
    topBar = {
      if (topBar != null) {
        Box(Modifier.windowInsetsPadding(barInsets)) { topBar() }
      }
    },
    bottomBar = {
      if (bottomBar != null) {
        Box(Modifier.windowInsetsPadding(barInsets)) { bottomBar() }
      }
    },
    snackbarHost = snackbarHost,
    floatingActionButton = floatingActionButton,
    floatingActionButtonPosition = floatingActionButtonPosition,
    containerColor = containerColor,
    contentColor = contentColor,
    contentWindowInsets = mergedInsets,
  ) { contentPadding ->
    val downstreamInsets = remember { MutableWindowInsets() }
    downstreamInsets.insets = PaddingValuesInsets(contentPadding).exclude(contentWindowInsets)
    CompositionLocalProvider(LocalScaffoldInsets provides downstreamInsets) {
      content(contentPadding)
    }
  }
}
