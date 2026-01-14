package app.rickandmorty.core.ui

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import kotlinx.collections.immutable.ImmutableList

@Stable
public interface NavigationSuiteState {
  public val topLevelDestinations: ImmutableList<TopLevelDestination>

  public val topLevelRoute: NavKey

  public val movableNavigationSuite: @Composable ((Modifier, NavigationSuiteType) -> Unit)
}
