package app.rickandmorty.core.coroutines.inject

import dev.zacsweers.metro.Qualifier

@Qualifier public annotation class DefaultDispatcher

@Qualifier public annotation class IODispatcher

@Qualifier public annotation class MainDispatcher

@Qualifier public annotation class MainImmediateDispatcher
