package app.rickandmorty.data.network

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.serialization.json.Json

class PagedResponseTest {
  private val json = Json { ignoreUnknownKeys = true }

  @Test
  fun decodesCharacterPage() {
    val response =
      json.decodeFromString(
        PagedResponse.serializer(CharacterResponse.serializer()),
        """
        {
          "info": {
            "count": 826,
            "pages": 42,
            "next": "https://rickandmortyapi.com/api/character?page=2",
            "prev": null
          },
          "results": [
            {
              "id": 1,
              "name": "Rick Sanchez",
              "status": "Alive",
              "species": "Human",
              "type": "",
              "gender": "Male",
              "origin": { "name": "Earth (C-137)", "url": "" },
              "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
              "episode": [],
              "url": "https://rickandmortyapi.com/api/character/1",
              "created": "2017-11-04T18:48:46.250Z"
            }
          ]
        }
        """,
      )

    assertEquals(826, response.info.count)
    assertEquals(2, response.info.nextPage)
    assertEquals("Rick Sanchez", response.results.single().name)
  }

  @Test
  fun decodesEpisodeAirDate() {
    val response =
      json.decodeFromString(
        PagedResponse.serializer(EpisodeResponse.serializer()),
        """
        {
          "info": { "count": 1, "next": null },
          "results": [
            { "id": 1, "name": "Pilot", "air_date": "December 2, 2013", "episode": "S01E01" }
          ]
        }
        """,
      )

    assertEquals("December 2, 2013", response.results.single().airDate)
  }

  @Test
  fun lastPageHasNoNextPage() {
    assertNull(PagedResponse.Info(count = 1, next = null).nextPage)
  }

  @Test
  fun nextPageIgnoresOtherQueryParameters() {
    val info =
      PagedResponse.Info(
        count = 1,
        next = "https://rickandmortyapi.com/api/character?page=3&name=rick",
      )

    assertEquals(3, info.nextPage)
  }
}
