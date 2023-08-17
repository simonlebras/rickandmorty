package app.rickandmorty.navigation

import android.content.Context
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import app.rickandmorty.feature.settings.navigation.settings
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

fun NavGraphBuilder.settings(
    context: Context,
    navController: NavHostController,
) {
    settings(
        navController = navController,
        onNavigateUp = navController::navigateUp,
        onNavigateToOssLicenses = {
            context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
        },
    )
}
