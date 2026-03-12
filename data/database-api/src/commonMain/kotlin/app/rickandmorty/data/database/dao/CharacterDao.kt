package app.rickandmorty.data.database.dao

import androidx.paging.PagingSource
import androidx.room3.Dao
import androidx.room3.DaoReturnTypeConverters
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.paging.PagingSourceDaoReturnTypeConverter
import app.rickandmorty.data.database.entity.CharacterEntity

@Dao
@DaoReturnTypeConverters(PagingSourceDaoReturnTypeConverter::class)
public interface CharacterDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertAll(characters: List<CharacterEntity>)

  @Transaction
  @Query(
    """
              SELECT character.* FROM character
              INNER JOIN character_paged_entry ON character_paged_entry.character_id =
   character.id
              ORDER BY page ASC, `index` ASC
          """
  )
  public fun getPagedCharacters(): PagingSource<Int, CharacterEntity>
}
