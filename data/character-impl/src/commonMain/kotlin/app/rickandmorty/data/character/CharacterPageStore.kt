package app.rickandmorty.data.character

import app.rickandmorty.data.database.dao.CharacterDao
import app.rickandmorty.data.database.dao.CharacterPagedEntryDao
import app.rickandmorty.data.database.entity.CharacterEntity
import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity
import app.rickandmorty.data.rest.nextPage
import app.rickandmorty.data.store.PageStore
import app.rickandmorty.data.store.StorePersistence
import app.rickandmorty.data.store.StoredPage
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
@SingleIn(AppScope::class)
internal class CharacterPageStore(
  private val remoteDataSource: CharacterRemoteDataSource,
  persistence: StorePersistence,
  private val characterDao: CharacterDao,
  private val characterPagedEntryDao: CharacterPagedEntryDao,
) : PageStore<CharacterEntity>(name = "character_pages", persistence = persistence) {
  override suspend fun fetch(page: Int): StoredPage<CharacterEntity> {
    val (info, results) = remoteDataSource.getCharacters(page)
    return StoredPage(
      entities = results.map { it.toCharacterEntity() },
      nextPage = info.nextPage(page),
    )
  }

  override fun read(page: Int): Flow<StoredPage<CharacterEntity>?> =
    characterDao.getCharacterPage(page).map { entries ->
      entries.keys.firstOrNull()?.let { first ->
        StoredPage(entities = entries.values.toList(), nextPage = first.nextPage)
      }
    }

  override suspend fun write(page: Int, stored: StoredPage<CharacterEntity>) {
    characterPagedEntryDao.deletePage(page)
    if (stored.nextPage == null) characterPagedEntryDao.deletePagesAfter(page)
    characterDao.upsertAll(stored.entities)
    characterPagedEntryDao.insertAll(
      stored.entities.mapIndexed { index, character ->
        CharacterPagedEntryEntity(
          page = page,
          nextPage = stored.nextPage,
          index = index,
          characterId = character.id,
        )
      },
    )
  }

  override suspend fun deletePage(page: Int) {
    characterPagedEntryDao.deletePage(page)
  }

  override suspend fun deleteAll() {
    characterPagedEntryDao.deleteAll()
  }
}

private fun RemoteCharacter.toCharacterEntity(): CharacterEntity =
  CharacterEntity(
    id = id.toString(),
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
  )
