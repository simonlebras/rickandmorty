package app.rickandmorty.data.character

import app.rickandmorty.core.paging.Page
import app.rickandmorty.data.database.entity.CharacterEntity
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.flow.Flow

@ContributesBinding(AppScope::class)
internal class CharacterRepositoryImpl(private val pages: CharacterPageStore) :
  CharacterRepository {
  override fun getCharacterPage(page: Int): Flow<Page<Character>> =
    pages.page(page) { it.toCharacter() }

  override suspend fun refreshCharacters() {
    pages.invalidateAll()
  }
}

private fun CharacterEntity.toCharacter(): Character =
  Character(
    id = id,
    name = name,
    status = Character.Status.from(status),
    species = Character.Species.from(species),
    type = type,
    gender = Character.Gender.from(gender),
    image = image,
  )
