package app.rickandmorty.data.database

import androidx.room.RoomDatabase
import androidx.room.withTransaction

internal class RoomTransactionRunner(private val database: RoomDatabase) : TransactionRunner {
  override suspend fun <T> invoke(block: suspend () -> T): T = database.withTransaction(block)
}
