package app.rickandmorty.ui.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.rickandmorty.core.metro.ViewModelKey
import app.rickandmorty.core.metro.ViewModelScope
import app.rickandmorty.data.character.Character
import app.rickandmorty.data.character.CharacterRepository
import dev.zacsweers.metro.ContributesIntoMap
import kotlinx.coroutines.flow.Flow

@ContributesIntoMap(ViewModelScope::class)
@ViewModelKey(CharacterListViewModel::class)
public class CharacterListViewModel(characterRepository: CharacterRepository) : ViewModel() {
  public val characters: Flow<PagingData<Character>> =
    characterRepository
      .getPagedCharacters(config = PagingConfig(pageSize = 24))
      .cachedIn(viewModelScope)
}
