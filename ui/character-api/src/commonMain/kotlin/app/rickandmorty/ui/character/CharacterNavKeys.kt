package app.rickandmorty.ui.character

import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.serialization.NavKeySerializer
import kotlinx.serialization.Serializable

@Serializable @NavKeySerializer(UiScope::class) public data object CharacterListNavKey : NavKey
