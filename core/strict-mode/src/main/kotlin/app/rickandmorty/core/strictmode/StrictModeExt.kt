package app.rickandmorty.core.strictmode

import android.os.StrictMode

public inline fun <T> allowThreadDiskReads(block: () -> T): T {
    val oldPolicy = StrictMode.allowThreadDiskReads()
    return try {
        block()
    } finally {
        StrictMode.setThreadPolicy(oldPolicy)
    }
}
