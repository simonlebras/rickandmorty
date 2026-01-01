package app.rickandmorty.core.paging

public interface PagedEntry {
  public val page: Int
  public val nextPage: Int?
}
