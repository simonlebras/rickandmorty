package app.rickandmorty.characters

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

public const val charactersRoute: String = "characters"

public fun NavController.navigateToCharacters(navOptions: NavOptions? = null) {
    navigate(charactersRoute, navOptions)
}

public fun NavGraphBuilder.characters(
    onNavigateToSettings: () -> Unit,
) {
    composable(route = charactersRoute) {
        CharactersScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
