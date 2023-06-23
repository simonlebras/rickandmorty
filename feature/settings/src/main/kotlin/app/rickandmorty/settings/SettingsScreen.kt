package app.rickandmorty.settings

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.rickandmorty.designsystem.icon.RamIcons
import app.rickandmorty.ui.resources.R as UiR

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SettingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToOssLicenses: () -> Unit,
) {
    ReportDrawn()

    Scaffold(
        topBar = {
            SettingsAppBar(
                onNavigateUp = onNavigateUp,
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
                .fillMaxSize(),
        ) {
            aboutSettings(
                onNavigateToOssLicenses = onNavigateToOssLicenses,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsAppBar(
    onNavigateUp: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.settings_title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = RamIcons.Filled.ArrowBack,
                    contentDescription = stringResource(UiR.string.navigate_up),
                )
            }
        },
    )
}
