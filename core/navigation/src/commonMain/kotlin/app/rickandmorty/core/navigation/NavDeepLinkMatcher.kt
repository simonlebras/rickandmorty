package app.rickandmorty.core.navigation

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.deeplink.DeepLinkMatcher
import androidx.navigation3.runtime.deeplink.DeepLinkRequest
import androidx.navigation3.runtime.deeplink.DeepLinkUri
import androidx.navigation3.runtime.deeplink.UriDeepLinkMatcher
import kotlinx.serialization.KSerializer

public fun interface NavDeepLinkMatcher {
  public fun match(request: DeepLinkRequest): DeepLinkMatcher.MatchResult<NavKey>?
}

public fun NavDeepLinkMatcher(
  matcher: DeepLinkMatcher<NavKey, DeepLinkMatcher.MatchResult<NavKey>>,
): NavDeepLinkMatcher = NavDeepLinkMatcher(matcher::match)

public fun <T : NavKey> DeepLinkConfig.matcher(
  path: String,
  serializer: KSerializer<T>,
): NavDeepLinkMatcher =
  NavDeepLinkMatcher(
    UriDeepLinkMatcher(
      uriPattern = DeepLinkUri("$baseUri/$path"),
      serializer = serializer,
    ),
  )
