package app.rickandmorty.core.strictmode

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class StrictModeExecutor
