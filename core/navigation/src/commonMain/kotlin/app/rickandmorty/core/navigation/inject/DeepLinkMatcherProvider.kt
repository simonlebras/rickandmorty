package app.rickandmorty.core.navigation.inject

import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.NavDeepLinkMatcher
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Multibinds

@BindingContainer
@ContributesTo(UiScope::class)
public interface DeepLinkMatcherProvider {
  @Multibinds(allowEmpty = true) public val deepLinkMatchers: Set<NavDeepLinkMatcher>
}
