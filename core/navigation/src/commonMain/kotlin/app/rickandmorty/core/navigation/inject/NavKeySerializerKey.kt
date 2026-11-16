package app.rickandmorty.core.navigation.inject

import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
public annotation class NavKeySerializerKey(val value: KClass<out NavKey>)
