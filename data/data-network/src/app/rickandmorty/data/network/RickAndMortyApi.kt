package app.rickandmorty.data.network

public interface RickAndMortyApi {
  public suspend fun getCharacters(page: Int): PagedResponse<CharacterResponse>

  public suspend fun getEpisodes(page: Int): PagedResponse<EpisodeResponse>

  public suspend fun getLocations(page: Int): PagedResponse<LocationResponse>
}
