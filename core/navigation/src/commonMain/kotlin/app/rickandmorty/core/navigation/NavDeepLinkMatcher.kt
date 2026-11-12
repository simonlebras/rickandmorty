package app.rickandmorty.core.navigation

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.deeplink.DeepLinkMatcher
import androidx.navigation3.runtime.deeplink.DeepLinkRequest

public fun interface NavDeepLinkMatcher {
  public fun match(request: DeepLinkRequest): DeepLinkMatcher.MatchResult<NavKey>?
}

public fun NavDeepLinkMatcher(
  matcher: DeepLinkMatcher<NavKey, DeepLinkMatcher.MatchResult<NavKey>>,
): NavDeepLinkMatcher = NavDeepLinkMatcher(matcher::match)
