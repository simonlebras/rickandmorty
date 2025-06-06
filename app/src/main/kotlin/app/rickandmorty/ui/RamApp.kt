package app.rickandmorty.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RamApp(modifier: Modifier = Modifier) {
  NavigationSuiteScaffold(modifier = modifier, navigationSuiteItems = {}) {}
}
