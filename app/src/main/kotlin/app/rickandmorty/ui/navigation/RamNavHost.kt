package app.rickandmorty.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun RamNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = CharacterList,
        modifier = modifier,
    ) {
        characterList(
            onNavigateToSettings = navController::navigateToMainSettings,
        )

        episodeList(
            onNavigateToSettings = navController::navigateToMainSettings,
        )

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
