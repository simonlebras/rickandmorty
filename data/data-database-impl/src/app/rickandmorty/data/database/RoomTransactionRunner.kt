package app.rickandmorty.data.database

import androidx.room3.deferredTransaction
import androidx.room3.immediateTransaction
import androidx.room3.useReaderConnection
import androidx.room3.useWriterConnection
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
internal class RoomTransactionRunner(private val database: RamDatabase) : TransactionRunner {
  override suspend fun <T> readTransaction(block: suspend () -> T): T {
    return database.useReaderConnection { transactor -> transactor.deferredTransaction { block() } }
  }

  override suspend fun <T> writeTransaction(block: suspend () -> T): T {
    return database.useWriterConnection { transactor ->
      transactor.immediateTransaction { block() }
    }
  }
}
