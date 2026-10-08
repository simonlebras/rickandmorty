package app.rickandmorty.core.paging

public fun interface PageFetcher {
  public suspend fun fetch(page: Int): PageResult
}
