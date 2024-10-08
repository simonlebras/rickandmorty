package app.rickandmorty.data.paging

public fun interface PageFetcher {
    public suspend fun fetch(page: Int): PageResult
}
