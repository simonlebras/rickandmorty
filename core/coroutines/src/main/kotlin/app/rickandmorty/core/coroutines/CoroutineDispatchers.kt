package app.rickandmorty.core.coroutines

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
public annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
public annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
public annotation class MainDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
public annotation class MainImmediateDispatcher
