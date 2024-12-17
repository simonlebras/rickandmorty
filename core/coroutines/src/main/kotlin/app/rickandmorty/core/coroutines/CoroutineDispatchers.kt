package app.rickandmorty.core.coroutines

@Retention(AnnotationRetention.RUNTIME)
public annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
public annotation class IODispatcher

@Retention(AnnotationRetention.RUNTIME)
public annotation class MainDispatcher

@Retention(AnnotationRetention.RUNTIME)
public annotation class MainImmediateDispatcher
