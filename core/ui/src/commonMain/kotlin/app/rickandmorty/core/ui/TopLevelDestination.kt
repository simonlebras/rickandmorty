package app.rickandmorty.core.ui

import androidx.navigation3.runtime.NavKey
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

public data class TopLevelDestination(
  val route: NavKey,
  val selectedIcon: DrawableResource,
  val unselectedIcon: DrawableResource,
  val label: StringResource,
)
