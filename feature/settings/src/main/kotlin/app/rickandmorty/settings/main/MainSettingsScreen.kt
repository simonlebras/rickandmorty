package app.rickandmorty.settings.main

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.designsystem.icon.RamIcons
import app.rickandmorty.jankstats.TrackScrollJank
import app.rickandmorty.locale.domain.Locale
import app.rickandmorty.settings.Header
import app.rickandmorty.settings.R
import app.rickandmorty.settings.SettingsContentType
import app.rickandmorty.settings.loader
import app.rickandmorty.settings.utils.versionName
import app.rickandmorty.ui.resources.R as UiR

@Composable
internal fun MainSettingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToLanguageSettings: () -> Unit,
    onNavigateToOssLicenses: () -> Unit,
    viewModel: MainSettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportDrawnWhen { !uiState.isLoading }

    MainSettingsScreen(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onNavigateToLanguageSettings = onNavigateToLanguageSettings,
        onNavigateToOssLicenses = onNavigateToOssLicenses,
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MainSettingsScreen(
    uiState: MainSettingsUiState,
    onNavigateUp: () -> Unit,
    onNavigateToLanguageSettings: () -> Unit,
    onNavigateToOssLicenses: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            MainSettingsAppBar(
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { padding ->
        val listState = rememberLazyListState()

        TrackScrollJank(
            scrollableState = listState,
            stateName = "mainSettings:screen",
        )

        LazyColumn(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize()
                .consumeWindowInsets(padding),
            state = listState,
            contentPadding = padding,
        ) {
            when {
                uiState.isLoading -> {
                    loader()
                }

                else -> {
                    generalSettings(
                        applicationLocale = uiState.applicationLocale(),
                        onNavigateToLanguageSettings = onNavigateToLanguageSettings,
                    )

                    aboutSettings(
                        onNavigateToOssLicenses = onNavigateToOssLicenses,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSettingsAppBar(
    onNavigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
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
        scrollBehavior = scrollBehavior,
    )
}

private fun LazyListScope.generalSettings(
    applicationLocale: Locale?,
    onNavigateToLanguageSettings: () -> Unit,
) {
    item(
        key = "general",
        contentType = SettingsContentType.HEADER,
    ) {
        Header(
            text = stringResource(R.string.settings_general_title),
            modifier = Modifier.fillMaxWidth(),
        )
    }

    item(
        key = "language",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(R.string.settings_language_title))
            },
            modifier = Modifier
                .semantics(mergeDescendants = true) { }
                .clickable(
                    onClickLabel = stringResource(R.string.settings_language_tap_action),
                    onClick = onNavigateToLanguageSettings,
                ),
            supportingContent = {
                val localeName = applicationLocale?.getDisplayName(applicationLocale)
                    ?: stringResource(R.string.settings_language_system_default)
                Text(text = localeName)
            },
        )
    }
}

private fun LazyListScope.aboutSettings(
    onNavigateToOssLicenses: () -> Unit,
) {
    item(
        key = "about",
        contentType = SettingsContentType.HEADER,
    ) {
        Header(
            text = stringResource(R.string.settings_about_title),
            modifier = Modifier.fillMaxWidth(),
        )
    }

    item(
        key = "oss_licenses",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(R.string.settings_oss_licenses_title))
            },
            modifier = Modifier
                .semantics(mergeDescendants = true) { }
                .clickable(
                    onClickLabel = stringResource(R.string.settings_oss_licenses_tap_action),
                    onClick = onNavigateToOssLicenses,
                ),
            supportingContent = {
                Text(text = stringResource(R.string.settings_oss_licenses_summary))
            },
        )
    }

    item(
        key = "app_version",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(R.string.settings_app_version))
            },
            modifier = Modifier.semantics(mergeDescendants = true) { },
            supportingContent = {
                Text(text = LocalContext.current.versionName)
            },
        )
    }
}
