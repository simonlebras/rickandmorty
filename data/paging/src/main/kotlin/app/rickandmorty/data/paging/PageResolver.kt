package app.rickandmorty.data.paging

public fun interface PageResolver<T> {
    public suspend fun resolve(item: T): Int
}
