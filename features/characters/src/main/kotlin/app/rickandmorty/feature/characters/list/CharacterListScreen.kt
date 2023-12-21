package app.rickandmorty.feature.characters.list

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.ui.resources.R as UiR
import app.rickandmorty.feature.characters.R

@Composable
internal fun CharacterListScreen(
    onNavigateToSettings: () -> Unit,
) {
    ReportDrawn()

    Scaffold(
        topBar = {
            CharacterListScreenAppBar(
                onNavigateToSettings = onNavigateToSettings,
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding),
        ) {
            Text(text = "Character List")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListScreenAppBar(
    onNavigateToSettings: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.character_list_title))
        },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    imageVector = RamIcons.Filled.Settings,
                    contentDescription = stringResource(UiR.string.navigate_up),
                )
            }
        },
    )
}
