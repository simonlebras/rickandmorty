package app.rickandmorty.inject

import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.DeepLinkConfig
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(UiScope::class)
object DeepLinkConfigProvider {
  @Provides
  fun provideDeepLinkConfig(): DeepLinkConfig = DeepLinkConfig(baseUri = "app://rickandmorty")
}
