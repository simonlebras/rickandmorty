package app.rickandmorty.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.ui.character.list.CharacterListScreen
import kotlinx.serialization.Serializable

@Serializable
data object CharacterList

fun NavHostController.navigateToCharacterList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = CharacterList,
        builder = builder,
    )
}

fun NavGraphBuilder.characterList(
    onNavigateToSettings: () -> Unit,
) {
    composable<CharacterList> {
        CharacterListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
