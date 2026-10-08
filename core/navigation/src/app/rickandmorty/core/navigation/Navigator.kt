package app.rickandmorty.core.navigation

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.transform

@Inject
@SingleIn(UiScope::class)
@Stable
public class Navigator(private val state: NavigationState) {
  private val reselectedRoutes = MutableSharedFlow<NavKey>(extraBufferCapacity = 1)

  public val currentRoute: NavKey?
    get() = state.currentRoute

  public fun reselectEvents(route: NavKey): Flow<Unit> =
    reselectedRoutes.filter { it == route }.transform { emit(Unit) }

  public fun navigate(route: NavKey, popUpTo: NavKey? = null, inclusive: Boolean = false) {
    if (route in state.backStacks.keys) {
      if (state.topLevelRoute == route) {
        reselectedRoutes.tryEmit(route)
      }

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
