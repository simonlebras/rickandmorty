package app.rickandmorty.core.coroutines.inject

import me.tatarka.inject.annotations.Qualifier

@Qualifier
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.TYPE,
)
public annotation class ApplicationScope
