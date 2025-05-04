package app.rickandmorty.core.metro

import androidx.lifecycle.ViewModel
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
public annotation class ViewModelKey(val value: KClass<out ViewModel>)
