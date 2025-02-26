package app.rickandmorty.core.coroutines

import app.rickandmorty.core.base.allowThreadDiskReads
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

public fun <T> Deferred<T>.awaitBlocking(): T = allowThreadDiskReads {
    runBlocking {
        await()
    }
}
