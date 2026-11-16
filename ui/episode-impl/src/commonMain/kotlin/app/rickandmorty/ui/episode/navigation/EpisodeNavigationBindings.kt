package app.rickandmorty.ui.episode.navigation

import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.DeepLinkConfig
import app.rickandmorty.core.navigation.NavDeepLinkMatcher
import app.rickandmorty.core.navigation.inject.NavKeySerializerKey
import app.rickandmorty.core.navigation.matcher
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.IntoMap
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides
import kotlinx.serialization.KSerializer

@BindingContainer
@ContributesTo(UiScope::class)
internal object EpisodeNavigationBindings {
  @Provides
  @IntoMap
  @NavKeySerializerKey(EpisodeListNavKey::class)
  fun provideSerializer(): KSerializer<out NavKey> = EpisodeListNavKey.serializer()

  @Provides
  @IntoSet
  fun provideDeepLinkMatcher(config: DeepLinkConfig): NavDeepLinkMatcher =
    config.matcher(path = "episodes", serializer = EpisodeListNavKey.serializer())
}
