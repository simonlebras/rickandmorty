package app.rickandmorty.core.ui

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import org.jetbrains.compose.resources.StringResource

public data class TopLevelDestination(
  val route: NavKey,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val label: StringResource,
)
