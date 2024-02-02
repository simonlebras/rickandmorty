package app.rickandmorty.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
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
import androidx.compose.ui.platform.LocalLayoutDirection
import app.rickandmorty.core.designsystem.component.util.LocalScaffoldContentPadding
import app.rickandmorty.core.designsystem.component.util.PaddingValuesInsets
import app.rickandmorty.core.ui.minus
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
public fun HazeScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    appBarContainerColor: Color = MaterialTheme.colorScheme.surface,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    blurTopBar: Boolean = false,
    blurBottomBar: Boolean = false,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    val hazeState = remember { HazeState() }

    NestedScaffold(
        modifier = modifier,
        topBar = {
            if (blurTopBar) {
                Box(
                    modifier = Modifier.hazeChild(
                        state = hazeState,
                        style = HazeMaterials.thin(),
                    ),
                ) {
                    topBar()
                }
            } else {
                topBar()
            }
        },
        bottomBar = {
            if (blurBottomBar) {
                Box(
                    modifier = Modifier.hazeChild(
                        state = hazeState,
                        style = HazeMaterials.thin(),
                    ),
                ) {
                    bottomBar()
                }
            } else {
                bottomBar()
            }
        },
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
    ) { contentPadding ->
        Box(
            modifier = Modifier.haze(
                state = hazeState,
                style = HazeDefaults.style(backgroundColor = appBarContainerColor),
            ),
        ) {
            content(contentPadding)
        }
    }
}

/**
 * A copy of Material 3's [Scaffold] composable, but with a few tweaks:
 *
 * - Supports being used nested. The `contentPadding` is compounded on each level.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun NestedScaffold(
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

    val insets = remember {
        MutableWindowInsets(contentWindowInsets.add(PaddingValuesInsets(upstreamContentPadding)))
    }

    val layoutDirection = LocalLayoutDirection.current

    LaunchedEffect(contentWindowInsets, upstreamContentPadding, layoutDirection) {
        insets.insets = contentWindowInsets.add(PaddingValuesInsets(upstreamContentPadding))
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
        contentWindowInsets = insets,
    ) { contentPadding ->
        val contentPaddingMinusInsets = contentPadding.minus(
            padding = contentWindowInsets.asPaddingValues(),
            layoutDirection = layoutDirection,
        )

        CompositionLocalProvider(LocalScaffoldContentPadding provides contentPaddingMinusInsets) {
            content(contentPadding)
        }
    }
}
