package app.rickandmorty.feature.characters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.characters.list.CharacterListScreen
import kotlinx.serialization.Serializable

@Serializable
public data object CharacterList

public fun NavHostController.navigateToCharacterList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = CharacterList,
        builder = builder,
    )
}

public fun NavGraphBuilder.characterList(
    onNavigateToSettings: () -> Unit,
) {
    composable<CharacterList> {
        CharacterListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
