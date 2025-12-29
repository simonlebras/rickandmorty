package app.rickandmorty.core.navigation.inject

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import app.rickandmorty.core.metro.UiScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provides
import kotlin.reflect.KClass
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private typealias NavKeySerializers = Map<KClass<out NavKey>, KSerializer<out NavKey>>

@ContributesTo(UiScope::class)
public interface SavedStateConfigurationProvider {
  @Multibinds(allowEmpty = true) public val navKeySerializers: NavKeySerializers

  public companion object {
    @Provides
    public fun provideSavedStateConfiguration(
      navKeySerializers: NavKeySerializers
    ): SavedStateConfiguration = SavedStateConfiguration {
      serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
          navKeySerializers.forEach { (keyClass, serializer) ->
            @Suppress("UNCHECKED_CAST")
            subclass(keyClass as KClass<NavKey>, serializer as KSerializer<NavKey>)
          }
        }
      }
    }
  }
}
