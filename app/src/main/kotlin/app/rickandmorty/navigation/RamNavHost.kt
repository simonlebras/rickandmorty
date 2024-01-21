package app.rickandmorty.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.rickandmorty.feature.characters.navigation.characterList
import app.rickandmorty.feature.characters.navigation.characterListRoute
import app.rickandmorty.feature.episodes.navigation.episodeList
import app.rickandmorty.feature.locations.navigation.locationList
import app.rickandmorty.feature.settings.navigation.navigateToMainSettings
import app.rickandmorty.feature.settings.navigation.settings
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun RamNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = characterListRoute,
        modifier = modifier,
    ) {
        characterList(
            onNavigateToSettings = navController::navigateToMainSettings,
            onNavigateToCharacterDetails = {},
        )

        episodeList()

        locationList(
            onNavigateToSettings = navController::navigateToMainSettings,
        )

        settings(
            navController = navController,
            onNavigateUp = navController::navigateUp,
            onNavigateToOssLicenses = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            },
        )
    }
}
