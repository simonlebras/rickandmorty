package app.rickandmorty.feature.home

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun HomeNavigationScaffold(
    modifier: Modifier = Modifier,
    startBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    val safeInsets = remember(contentWindowInsets) {
        MutableWindowInsets(contentWindowInsets)
    }
    Surface(
        modifier = modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
            // Exclude currently consumed window insets from user provided contentWindowInsets
            safeInsets.insets = contentWindowInsets.exclude(consumedWindowInsets)
        },
        color = containerColor,
        contentColor = contentColor,
    ) {
        HomeNavigationScaffoldLayout(
            startBar = startBar,
            bottomBar = bottomBar,
            content = content,
            contentWindowInsets = safeInsets,
        )
    }
}

@Composable
private fun HomeNavigationScaffoldLayout(
    startBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    contentWindowInsets: WindowInsets,
    bottomBar: @Composable () -> Unit,
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(layoutWidth, layoutHeight) {
            val startBarPlaceables = subcompose(ScaffoldLayoutContent.StartBar, startBar).fastMap {
                it.measure(looseConstraints)
            }

            val startBarWidth = startBarPlaceables.fastMaxBy { it.width }?.width

            val bottomBarPlaceables =
                subcompose(ScaffoldLayoutContent.BottomBar, bottomBar).fastMap {
                    it.measure(looseConstraints)
                }

            val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height

            val bodyContentPlaceables = subcompose(ScaffoldLayoutContent.MainContent) {
                val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
                val innerPadding = PaddingValues(
                    start = if (startBarPlaceables.isEmpty() || startBarWidth == null) {
                        insets.calculateStartPadding((this@SubcomposeLayout).layoutDirection)
                    } else {
                        startBarWidth.toDp()
                    },
                    top = insets.calculateTopPadding(),
                    end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection),
                    bottom = if (bottomBarPlaceables.isEmpty() || bottomBarHeight == null) {
                        insets.calculateBottomPadding()
                    } else {
                        bottomBarHeight.toDp()
                    },
                )
                content(innerPadding)
            }.fastMap { it.measure(looseConstraints) }

            // Placing to control drawing order to match default elevation of each placeable

            bodyContentPlaceables.fastForEach {
                it.place(0, 0)
            }
            startBarPlaceables.fastForEach {
                it.placeRelative(0, 0)
            }
            // The bottom bar is always at the bottom of the layout
            bottomBarPlaceables.fastForEach {
                it.place(0, layoutHeight - (bottomBarHeight ?: 0))
            }
        }
    }
}

private enum class ScaffoldLayoutContent { MainContent, StartBar, BottomBar }
