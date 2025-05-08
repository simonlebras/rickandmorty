package app.rickandmorty.core.metro

import android.app.Activity
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.FIELD,
  AnnotationTarget.PROPERTY,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.CLASS,
  AnnotationTarget.TYPE,
)
@Retention(AnnotationRetention.RUNTIME)
public annotation class ActivityKey(val value: KClass<out Activity>)
