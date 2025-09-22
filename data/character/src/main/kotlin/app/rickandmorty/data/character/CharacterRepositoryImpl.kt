package app.rickandmorty.data.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import app.rickandmorty.data.database.TransactionRunner
import app.rickandmorty.data.database.dao.CharacterDao
import app.rickandmorty.data.database.dao.CharacterPagedEntryDao
import app.rickandmorty.data.database.entity.CharacterEntity
import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity
import app.rickandmorty.data.database.entity.toCharacter
import app.rickandmorty.data.model.Character
import app.rickandmorty.data.paging.FIRST_PAGE_KEY
import app.rickandmorty.data.paging.PageKeyedRemoteMediator
import app.rickandmorty.data.paging.PageResult
import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CharacterRepositoryImpl(
  private val apolloClient: ApolloClient,
  private val transactionRunner: TransactionRunner,
  private val characterDao: CharacterDao,
  private val characterPagedEntryDao: CharacterPagedEntryDao,
) : CharacterRepository {
  @OptIn(ExperimentalPagingApi::class)
  override fun getPagedCharacters(config: PagingConfig): Flow<PagingData<Character>> {
    val remoteMediator =
      PageKeyedRemoteMediator<CharacterEntity>(
        pagedEntryResolver = { character ->
          transactionRunner.readTransaction { characterPagedEntryDao.getPagedEntry(character.id) }
        },
        pageFetcher = { page ->
          val data =
            apolloClient.query(GetCharactersQuery(page = page)).execute().dataAssertNoErrors

          val (info, results) = data.characters!!

          val resultSize = results.size
          val characters = ArrayList<CharacterEntity>(resultSize)
          val pagedEntries = ArrayList<CharacterPagedEntryEntity>(resultSize)
          results.forEachIndexed { index, remoteCharacter ->
            val character =
              CharacterEntity(
                id = remoteCharacter.id,
                name = remoteCharacter.name,
                status = remoteCharacter.status,
                species = remoteCharacter.species,
                type = remoteCharacter.type,
                gender = remoteCharacter.gender,
                image = remoteCharacter.image,
              )
            characters.add(character)

            val pagedEntry =
              CharacterPagedEntryEntity(
                page = page,
                nextPage = info.next,
                index = index,
                characterId = character.id,
              )
            pagedEntries.add(pagedEntry)
          }
          transactionRunner.writeTransaction {
            if (page == FIRST_PAGE_KEY) {
              characterPagedEntryDao.deleteAll()
            }
            characterDao.insertAll(characters)
            characterPagedEntryDao.insertAll(pagedEntries)
          }

          return@PageKeyedRemoteMediator PageResult(count = info.count, nextPage = info.next)
        },
      )
    return Pager(
        config = config,
        remoteMediator = remoteMediator,
        pagingSourceFactory = { characterDao.getPagedCharacters() },
      )
      .flow
      .map { pagingData -> pagingData.map { character -> character.toCharacter() } }
  }
}
