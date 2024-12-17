package app.rickandmorty.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import app.rickandmorty.core.ui.LocalSharedTransitionScope

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
fun RamApp(modifier: Modifier = Modifier) {
    SharedTransitionLayout(
        modifier = modifier.semantics {
            testTagsAsResourceId = true
        },
    ) {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this,
        ) {
            NavigationSuiteScaffold(
                navigationSuiteItems = {
                },
            ) {
                Box(
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
                )
            }
        }
    }
}
