package app.rickandmorty.feature.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.rickandmorty.data.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {
    val characters = characterRepository
        .getPagedCharacters(
            config = PagingConfig(pageSize = 24),
        )
        .cachedIn(viewModelScope)
}
