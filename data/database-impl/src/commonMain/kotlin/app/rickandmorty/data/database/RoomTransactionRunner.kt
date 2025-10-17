package app.rickandmorty.data.database

import androidx.room.deferredTransaction
import androidx.room.immediateTransaction
import androidx.room.useReaderConnection
import androidx.room.useWriterConnection
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
public class RoomTransactionRunner(private val database: RamDatabase) : TransactionRunner {
  override suspend fun <T> readTransaction(block: suspend () -> T): T {
    return database.useReaderConnection { transactor -> transactor.deferredTransaction { block() } }
  }

  override suspend fun <T> writeTransaction(block: suspend () -> T): T {
    return database.useWriterConnection { transactor ->
      transactor.immediateTransaction { block() }
    }
  }
}
