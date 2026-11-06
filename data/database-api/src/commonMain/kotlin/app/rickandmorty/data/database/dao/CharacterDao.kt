package app.rickandmorty.data.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import app.rickandmorty.data.database.entity.CharacterEntity
import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface CharacterDao {
  @Upsert public suspend fun upsertAll(characters: List<CharacterEntity>)

  @Query(
    """
              SELECT * FROM character_paged_entry
              INNER JOIN character ON character.id = character_paged_entry.character_id
              WHERE page = :page
              ORDER BY `index` ASC
          """,
  )
  public fun getCharacterPage(page: Int): Flow<Map<CharacterPagedEntryEntity, CharacterEntity>>
}
