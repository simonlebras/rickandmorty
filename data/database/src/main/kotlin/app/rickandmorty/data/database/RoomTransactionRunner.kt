package app.rickandmorty.data.database

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import javax.inject.Inject
import se.ansman.dagger.auto.AutoBind

@AutoBind
internal class RoomTransactionRunner @Inject constructor(
    private val database: RoomDatabase,
) : TransactionRunner {
    override suspend fun <T> invoke(block: suspend () -> T): T = database.withTransaction(block)
}
