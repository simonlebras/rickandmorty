package app.rickandmorty.ui.character.navigation

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
internal object CharacterNavigationBindings {
  @Provides
  @IntoMap
  @NavKeySerializerKey(CharacterListNavKey::class)
  fun provideSerializer(): KSerializer<out NavKey> = CharacterListNavKey.serializer()

  @Provides
  @IntoSet
  fun provideDeepLinkMatcher(config: DeepLinkConfig): NavDeepLinkMatcher =
    config.matcher(path = "characters", serializer = CharacterListNavKey.serializer())
}
