package app.rickandmorty.core.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavKey

public class Navigator(private val state: NavigationState) {
  public fun navigate(route: NavKey) {
    if (route in state.backStacks.keys) {
      state.topLevelRoute = route
    } else {
      state.backStacks[state.topLevelRoute]?.add(route)
    }
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
