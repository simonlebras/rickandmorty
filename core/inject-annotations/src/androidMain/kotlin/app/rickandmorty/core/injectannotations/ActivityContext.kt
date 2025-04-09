package app.rickandmorty.core.injectannotations

import me.tatarka.inject.annotations.Qualifier

@Qualifier
@Target(
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.TYPE,
)
public annotation class ActivityContext
