package app.rickandmorty.core.base

public fun <T> unsafeLazy(initializer: () -> T): Lazy<T> =
  lazy(LazyThreadSafetyMode.NONE, initializer)
