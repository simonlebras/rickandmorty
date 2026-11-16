package app.rickandmorty.core.navigation.inject

import app.rickandmorty.core.metro.UiScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

@BindingContainer
@ContributesTo(UiScope::class)
public object SerializerBindings {
  @Provides public inline fun <reified T : Any> provideSerializer(): KSerializer<T> = serializer()
}
