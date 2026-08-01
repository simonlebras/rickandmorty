package app.rickandmorty.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreProvider
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.SavedState
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.serialization.SavedStateConfiguration
import androidx.savedstate.serialization.decodeFromSavedState
import androidx.savedstate.serialization.encodeToSavedState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.serialization.Serializable

private const val SavedStateKey = "app.rickandmorty.core.navigation.NavigationState"

@Stable
public class NavigationState(
  public val startRoute: NavKey,
  topLevelRoutes: Set<NavKey>,
  private val configuration: SavedStateConfiguration,
) : SavedStateRegistry.SavedStateProvider {
  public var topLevelRoute: NavKey by mutableStateOf(startRoute)
    internal set

  public val backStacks: PersistentMap<NavKey, NavBackStack<NavKey>> =
    topLevelRoutes.associateWith { route -> NavBackStack(route) }.toPersistentMap()

  public val currentRoute: NavKey?
    get() = backStacks[topLevelRoute]?.lastOrNull()

  private val topLevelRoutesInUse by derivedStateOf {
    if (this@NavigationState.topLevelRoute == startRoute) {
      persistentListOf(startRoute)
    } else {
      persistentListOf(startRoute, this@NavigationState.topLevelRoute)
    }
  }

  public fun attachTo(savedStateHandle: SavedStateHandle) {
    savedStateHandle.get<SavedState>(SavedStateKey)?.let(::restore)
    savedStateHandle.setSavedStateProvider(key = SavedStateKey, provider = this)
  }

  override fun saveState(): SavedState =
    encodeToSavedState(
      serializer = NavigationSnapshot.serializer(),
      value =
        NavigationSnapshot(
          topLevelRoute = topLevelRoute,
          backStacks =
            backStacks.map { (route, stack) ->
              NavigationSnapshot.BackStack(route, stack.toList())
            },
        ),
      configuration = configuration,
    )

  private fun restore(savedState: SavedState) {
    val snapshot =
      decodeFromSavedState(
        deserializer = NavigationSnapshot.serializer(),
        savedState = savedState,
        configuration = configuration,
      )

    topLevelRoute = snapshot.topLevelRoute
    snapshot.backStacks.forEach { (route, entries) ->
      backStacks[route]?.apply {
        clear()
        addAll(entries)
      }
    }
  }

  @Composable
  public fun toDecoratedEntries(entryProvider: EntryProvider): ImmutableList<NavEntry<NavKey>> {
    val decoratedEntries = backStacks.mapValues { (key, stack) ->
      val viewModelStoreProvider = rememberViewModelStoreProvider(key = key)
      val decorators =
        [
          rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
          rememberViewModelStoreNavEntryDecorator(viewModelStoreProvider),
        ]

      rememberDecoratedNavEntries(
        backStack = stack,
        entryDecorators = decorators,
        entryProvider = entryProvider,
      )
    }

    return remember(topLevelRoutesInUse, decoratedEntries) {
      topLevelRoutesInUse.flatMap { decoratedEntries[it].orEmpty() }.toImmutableList()
    }
  }

  @Serializable
  private class NavigationSnapshot(val topLevelRoute: NavKey, val backStacks: List<BackStack>) {
    @Serializable data class BackStack(val route: NavKey, val entries: List<NavKey>)
  }
}
