package app.rickandmorty.core.navigation

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.deeplink.BackStackMatchResult
import androidx.navigation3.runtime.deeplink.DeepLinkRequest
import app.rickandmorty.core.metro.UiScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(UiScope::class)
public class DeepLinkHandler(
  private val state: NavigationState,
  private val navigator: Navigator,
  private val matchers: Set<NavDeepLinkMatcher>,
) {
  public fun handle(request: DeepLinkRequest): Boolean {
    val matchResult = matchers.mapNotNull { it.match(request) }.maxOrNull() ?: return false

    val keys: List<NavKey> =
      when (matchResult) {
        is BackStackMatchResult<*, *> -> matchResult.backStack.filterIsInstance<NavKey>()
        else -> listOf(matchResult.key)
      }
    if (keys.isEmpty()) return false

    val root = keys.first()
    if (root in state.backStacks.keys) {
      if (root != state.startRoute) {
        resetStack(state.startRoute)
      }

      val targetStack = checkNotNull(state.backStacks[root])
      val wasAlreadyAtRoot = state.topLevelRoute == root && targetStack.size == 1 && keys.size == 1
      targetStack.clear()
      targetStack.addAll(keys)

      if (wasAlreadyAtRoot) {
        navigator.navigate(root)
      } else {
        state.topLevelRoute = root
      }
    } else {
      resetStack(state.startRoute)
      state.topLevelRoute = state.startRoute
      keys.forEach { navigator.navigate(it) }
    }

    return true
  }

  private fun resetStack(tab: NavKey) {
    val stack = state.backStacks[tab] ?: return
    if (stack.firstOrNull() != tab) {
      stack.clear()
      stack.add(tab)
      return
    }
    while (stack.size > 1) {
      stack.removeLastOrNull()
    }
  }
}
