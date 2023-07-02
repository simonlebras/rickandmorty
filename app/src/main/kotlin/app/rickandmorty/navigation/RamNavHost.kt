package app.rickandmorty.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.rickandmorty.characters.characters
import app.rickandmorty.characters.charactersRoute
import app.rickandmorty.episodes.episodes
import app.rickandmorty.locations.locations
import app.rickandmorty.settings.navigation.navigateToMainSettings
import app.rickandmorty.settings.navigation.settings
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun RamNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = charactersRoute,
        modifier = modifier,
    ) {
        characters(
            onNavigateToSettings = navController::navigateToMainSettings,
        )

        episodes()

        locations()

        settings(
            navController = navController,
            onNavigateUp = navController::navigateUp,
            onNavigateToOssLicenses = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            },
        )
    }
}
