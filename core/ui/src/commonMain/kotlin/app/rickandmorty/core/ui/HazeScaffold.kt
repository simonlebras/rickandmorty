package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.blur.blurEffect
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

@Composable
public fun HazeScaffold(
  modifier: Modifier = Modifier,
  topBar: @Composable (() -> Unit)? = null,
  bottomBar: @Composable (() -> Unit)? = null,
  snackbarHost: @Composable () -> Unit = {},
  floatingActionButton: @Composable () -> Unit = {},
  floatingActionButtonPosition: FabPosition = FabPosition.End,
  containerColor: Color = MaterialTheme.colorScheme.background,
  contentColor: Color = contentColorFor(containerColor),
  contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
  hazeState: HazeState = LocalHazeState.current,
  content: @Composable (PaddingValues) -> Unit,
) {
  NestedScaffold(
    modifier = modifier,
    topBar =
      if (topBar != null) {
        {
          Box(Modifier.hazeEffect(state = hazeState) { blurEffect { blurEnabled = true } }) {
            topBar()
          }
        }
      } else {
        null
      },
    bottomBar =
      if (bottomBar != null) {
        {
          Box(Modifier.hazeEffect(state = hazeState) { blurEffect { blurEnabled = true } }) {
            bottomBar()
          }
        }
      } else {
        null
      },
    snackbarHost = snackbarHost,
    floatingActionButton = floatingActionButton,
    floatingActionButtonPosition = floatingActionButtonPosition,
    containerColor = containerColor,
    contentColor = contentColor,
    contentWindowInsets = contentWindowInsets,
    content = { contentPadding ->
      Box(Modifier.hazeSource(state = hazeState)) { content(contentPadding) }
    },
  )
}
