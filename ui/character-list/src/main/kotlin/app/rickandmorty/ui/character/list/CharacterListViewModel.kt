package app.rickandmorty.ui.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.rickandmorty.data.character.CharacterRepository
import app.rickandmorty.data.model.Character
import kotlinx.coroutines.flow.Flow

public class CharacterListViewModel(characterRepository: CharacterRepository) : ViewModel() {
  public val characters: Flow<PagingData<Character>> =
    characterRepository
      .getPagedCharacters(config = PagingConfig(pageSize = 24))
      .cachedIn(viewModelScope)
}
