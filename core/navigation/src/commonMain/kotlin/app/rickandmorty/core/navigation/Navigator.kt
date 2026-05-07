package app.rickandmorty.core.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavKey

public class Navigator(private val state: NavigationState) {
  public val currentRoute: NavKey?
    get() = state.currentRoute

  public fun navigate(route: NavKey, popUpTo: NavKey? = null, inclusive: Boolean = false) {
    if (route in state.backStacks.keys) {
      state.topLevelRoute = route
      return
    }

    val currentStack =
      checkNotNull(state.backStacks[state.topLevelRoute]) {
        "Stack for ${state.topLevelRoute} not found"
      }
    if (popUpTo != null && popUpTo in currentStack) {
      while (currentStack.last() != popUpTo) {
        currentStack.removeLastOrNull()
      }
      if (inclusive) {
        currentStack.removeLastOrNull()
      }
    }
    currentStack.add(route)
  }

  public fun goBack() {
    val currentStack =
      checkNotNull(state.backStacks[state.topLevelRoute]) {
        "Stack for ${state.topLevelRoute} not found"
      }
    val currentRoute = currentStack.last()
    if (currentRoute == state.topLevelRoute) {
      state.topLevelRoute = state.startRoute
    } else {
      currentStack.removeLastOrNull()
    }
  }
}

@Suppress("ComposeCompositionLocalUsage")
public val LocalNavigator: ProvidableCompositionLocal<Navigator> = staticCompositionLocalOf {
  error("Navigator not provided")
}
