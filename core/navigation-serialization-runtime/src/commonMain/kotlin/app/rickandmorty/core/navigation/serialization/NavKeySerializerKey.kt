package app.rickandmorty.core.navigation.serialization

import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@MapKey public annotation class NavKeySerializerKey(val value: KClass<out NavKey>)
