package app.rickandmorty.data.paging

public fun interface PagedEntryResolver<T> {
  public suspend fun resolve(item: T): PagedEntry?
}
