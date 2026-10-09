package app.rickandmorty.inject

import androidx.savedstate.serialization.SavedStateConfiguration
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.NavigationState
import app.rickandmorty.ui.TopLevelNavigations
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@BindingContainer
@ContributesTo(UiScope::class)
object NavigationStateProvider {
  @Provides
  @SingleIn(UiScope::class)
  fun provideNavigationState(configuration: SavedStateConfiguration): NavigationState =
    NavigationState(
      startRoute = TopLevelNavigations.startRoute,
      topLevelRoutes = TopLevelNavigations.routes,
      configuration = configuration,
    )
}
