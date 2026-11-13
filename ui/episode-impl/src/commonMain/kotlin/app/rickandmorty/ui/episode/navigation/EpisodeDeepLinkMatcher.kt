package app.rickandmorty.ui.episode.navigation

import androidx.navigation3.runtime.deeplink.DeepLinkUri
import androidx.navigation3.runtime.deeplink.UriDeepLinkMatcher
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.DeepLinkConfig
import app.rickandmorty.core.navigation.NavDeepLinkMatcher
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(UiScope::class)
internal class EpisodeDeepLinkMatcher(
  config: DeepLinkConfig,
) :
  NavDeepLinkMatcher by NavDeepLinkMatcher(
    UriDeepLinkMatcher(
      uriPattern = DeepLinkUri("${config.baseUri}/episodes"),
      serializer = EpisodeListNavKey.serializer(),
    ),
  )
