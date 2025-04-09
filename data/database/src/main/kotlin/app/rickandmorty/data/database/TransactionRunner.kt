package app.rickandmorty.data.database

public interface TransactionRunner {
  public suspend operator fun <T> invoke(block: suspend () -> T): T
}
