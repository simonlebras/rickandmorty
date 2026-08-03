package app.rickandmorty.ui.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.Navigator
import app.rickandmorty.data.character.Character
import app.rickandmorty.data.character.CharacterRepository
import app.rickandmorty.ui.character.navigation.CharacterListNavKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.Flow

@ContributesIntoMap(UiScope::class)
@ViewModelKey
internal class CharacterListViewModel(
  characterRepository: CharacterRepository,
  navigator: Navigator,
) : ViewModel() {
  val characters: Flow<PagingData<Character>> =
    characterRepository
      .getPagedCharacters(config = PagingConfig(pageSize = 24))
      .cachedIn(viewModelScope)

  val scrollToTopEvents: Flow<Unit> = navigator.reselectEvents(CharacterListNavKey)
}
