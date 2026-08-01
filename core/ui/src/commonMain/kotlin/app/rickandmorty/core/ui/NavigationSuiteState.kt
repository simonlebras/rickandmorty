package app.rickandmorty.core.ui

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavKey
import kotlinx.collections.immutable.ImmutableList

@Stable
public interface NavigationSuiteState {
  public val topLevelDestinations: ImmutableList<TopLevelDestination>

  public val topLevelRoute: NavKey
}
