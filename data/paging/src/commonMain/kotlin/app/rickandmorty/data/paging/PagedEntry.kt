package app.rickandmorty.data.paging

public interface PagedEntry {
  public val page: Int
  public val nextPage: Int?
}
