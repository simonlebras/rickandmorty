package app.rickandmorty.hilt

import dagger.Lazy

public typealias HiltSet<T> = @JvmSuppressWildcards Set<T>

public typealias HiltMap<K, V> = @JvmSuppressWildcards Map<K, V>

public typealias HiltLazy<T> = @JvmSuppressWildcards Lazy<T>
