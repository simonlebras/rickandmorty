package app.rickandmorty.core.processlifecycle.inject

import me.tatarka.inject.annotations.Qualifier

@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.TYPE,
)
@Qualifier
public annotation class ProcessLifecycleOwner
