package app.rickandmorty.core.processlifecycle.inject

import dev.zacsweers.metro.Qualifier

@Qualifier
@Target(
  AnnotationTarget.CLASS,
  AnnotationTarget.FIELD,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.TYPE,
)
public annotation class ProcessLifecycleOwner
