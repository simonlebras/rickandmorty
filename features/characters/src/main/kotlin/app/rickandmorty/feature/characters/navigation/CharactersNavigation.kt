package app.rickandmorty.feature.characters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.characters.details.CharacterDetailsScreen
import app.rickandmorty.feature.characters.list.CharacterListScreen
import com.microsoft.device.dualscreen.twopanelayout.Screen
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneNavScope
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.composable as twopanelayoutComposable

public const val characterListRoute: String = "characterList"
public const val characterDetailsRoute: String = "characterDetails"

public fun NavHostController.navigateToCharacterList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = characterListRoute,
        builder = builder,
    )
}

context(TwoPaneNavScope)
public fun NavHostController.navigateToCharacterDetails(
    launchScreen: Screen,
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    navigateTo(
        route = characterDetailsRoute,
        launchScreen = launchScreen,
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

public fun NavGraphBuilder.characterDetails() {
    twopanelayoutComposable(route = characterDetailsRoute) {
        CharacterDetailsScreen()
    }
}
