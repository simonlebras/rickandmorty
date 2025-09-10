package app.rickandmorty.data.database

public interface TransactionRunner {
  public suspend fun <T> readTransaction(block: suspend () -> T): T

  public suspend fun <T> writeTransaction(block: suspend () -> T): T
}
