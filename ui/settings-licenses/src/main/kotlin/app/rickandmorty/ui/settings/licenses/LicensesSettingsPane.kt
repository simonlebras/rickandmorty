package app.rickandmorty.ui.settings.licenses

import app.rickandmorty.core.l10n.R as L10nR
import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.metrics.TrackScrollJank
import app.rickandmorty.data.model.License

@Composable
public fun LicensesSettingsPane(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LicensesSettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportDrawn()

    LicensesSettingsPane(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LicensesSettingsPane(
    uiState: LicenseSettingsUiState,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LicensesSettingsAppBar(
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { contentPadding ->
        val listState = rememberLazyListState()

        TrackScrollJank(
            scrollableState = listState,
            stateName = "licensesSettings:pane",
        )

        LazyColumn(
            modifier = Modifier
                .selectableGroup()
                .fillMaxSize()
                .consumeWindowInsets(contentPadding),
            state = listState,
            contentPadding = contentPadding,
        ) {
            uiState.licenses()?.let {
                items(it.size) { index ->
                    val license = it[index]
                    LicenseItem(license = license)
                }
            }
        }
    }
}

@Composable
private fun LicenseItem(license: License) {
    ListItem(
        headlineContent = { Text(text = license.artifactId) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LicensesSettingsAppBar(
    onNavigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(L10nR.string.settings_licenses_title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = RamIcons.Filled.ArrowBack,
                    contentDescription = stringResource(L10nR.string.navigate_up),
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}
