package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.minus
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalLayoutApi::class)
@Composable
public fun NestedScaffold(
  modifier: Modifier = Modifier,
  topBar: @Composable () -> Unit = {},
  bottomBar: @Composable () -> Unit = {},
  snackbarHost: @Composable () -> Unit = {},
  floatingActionButton: @Composable () -> Unit = {},
  floatingActionButtonPosition: FabPosition = FabPosition.End,
  containerColor: Color = MaterialTheme.colorScheme.background,
  contentColor: Color = contentColorFor(containerColor),
  contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
  content: @Composable (PaddingValues) -> Unit,
) {
  val upstreamContentPadding = LocalScaffoldContentPadding.current

  val mergedInsets = remember {
    MutableWindowInsets(contentWindowInsets.add(PaddingValuesInsets(upstreamContentPadding)))
  }
  LaunchedEffect(contentWindowInsets, upstreamContentPadding) {
    mergedInsets.insets = contentWindowInsets.add(PaddingValuesInsets(upstreamContentPadding))
  }

  Scaffold(
    modifier = modifier,
    topBar = topBar,
    bottomBar = bottomBar,
    snackbarHost = snackbarHost,
    floatingActionButton = floatingActionButton,
    floatingActionButtonPosition = floatingActionButtonPosition,
    containerColor = containerColor,
    contentColor = contentColor,
    contentWindowInsets = mergedInsets,
  ) { contentPadding ->
    val downstreamContentPadding = contentPadding - contentWindowInsets.asPaddingValues()
    CompositionLocalProvider(LocalScaffoldContentPadding provides downstreamContentPadding) {
      content(contentPadding)
    }
  }
}
