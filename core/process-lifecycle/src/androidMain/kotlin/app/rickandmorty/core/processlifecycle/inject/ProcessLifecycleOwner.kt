package app.rickandmorty.core.processlifecycle.inject

import me.tatarka.inject.annotations.Qualifier

@Qualifier
@Target(
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.TYPE,
)
public annotation class ProcessLifecycleOwner
