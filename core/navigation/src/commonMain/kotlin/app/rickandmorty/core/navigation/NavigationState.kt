package app.rickandmorty.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastFlatMap
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.serialization.PolymorphicSerializer

@Composable
public fun rememberNavigationState(
  startRoute: NavKey,
  topLevelRoutes: Set<NavKey>,
  configuration: SavedStateConfiguration,
): NavigationState {
  val topLevelRoute =
    rememberSerializable(
      startRoute,
      topLevelRoutes,
      serializer = MutableStateSerializer(PolymorphicSerializer(NavKey::class)),
      configuration = configuration,
    ) {
      mutableStateOf(startRoute)
    }

  val backStacks =
    topLevelRoutes
      .associateWith { key -> rememberNavBackStack(configuration = configuration, key) }
      .toPersistentMap()

  return remember(startRoute, topLevelRoutes) {
    NavigationState(startRoute = startRoute, topLevelRoute = topLevelRoute, backStacks = backStacks)
  }
}

@Stable
public class NavigationState(
  public val startRoute: NavKey,
  topLevelRoute: MutableState<NavKey>,
  public val backStacks: PersistentMap<NavKey, NavBackStack<NavKey>>,
) {
  public var topLevelRoute: NavKey by topLevelRoute
    internal set

  private val topLevelRoutesInUse by derivedStateOf {
    if (this@NavigationState.topLevelRoute == startRoute) {
      persistentListOf(startRoute)
    } else {
      persistentListOf(startRoute, this@NavigationState.topLevelRoute)
    }
  }

  @Composable
  public fun toDecoratedEntries(entryProvider: EntryProvider): PersistentList<NavEntry<NavKey>> {
    val decorators = listOf(rememberSaveableStateHolderNavEntryDecorator<NavKey>())

    val decoratedEntries =
      backStacks.mapValues { (_, stack) ->
        rememberDecoratedNavEntries(
          backStack = stack,
          entryDecorators = decorators,
          entryProvider = entryProvider,
        )
      }

    return remember(topLevelRoutesInUse, decoratedEntries) {
      topLevelRoutesInUse.fastFlatMap { decoratedEntries[it].orEmpty() }.toPersistentList()
    }
  }
}
