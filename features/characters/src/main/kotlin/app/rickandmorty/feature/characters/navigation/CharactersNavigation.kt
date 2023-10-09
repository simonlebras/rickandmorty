package app.rickandmorty.feature.characters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.characters.list.CharacterListScreen

public const val characterListRoute: String = "characterList"
public const val characterDetailsRoute: String = "characterDetails"

public fun NavHostController.navigateToCharacterList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = characterListRoute,
        builder = builder,
    )
}

public fun NavGraphBuilder.characterList(
    onNavigateToSettings: () -> Unit,
    onNavigateToCharacterDetails: () -> Unit,
) {
    composable(route = characterListRoute) {
        CharacterListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
