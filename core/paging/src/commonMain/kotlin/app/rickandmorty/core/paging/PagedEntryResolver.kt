package app.rickandmorty.core.paging

public fun interface PagedEntryResolver<T> {
  public suspend fun resolve(item: T): PagedEntry?
}
