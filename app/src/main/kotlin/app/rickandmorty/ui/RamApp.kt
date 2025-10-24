package app.rickandmorty.ui

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.rickandmorty.ui.location.list.LocationListScreen

@Composable
fun RamApp(modifier: Modifier = Modifier) {
  NavigationSuiteScaffold(navigationItems = {}, modifier = modifier) {
    LocationListScreen(onNavigateToSettings = {})
  }
}
