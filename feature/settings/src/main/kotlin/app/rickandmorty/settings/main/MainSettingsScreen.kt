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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.designsystem.icon.RamIcons
import app.rickandmorty.jankstats.TrackScrollJank
import app.rickandmorty.locale.domain.Locale
import app.rickandmorty.settings.Header
import app.rickandmorty.settings.R
import app.rickandmorty.settings.SettingsContentType
import app.rickandmorty.settings.loader
import app.rickandmorty.settings.utils.label
import app.rickandmorty.settings.utils.versionName
import app.rickandmorty.theme.domain.Theme
import app.rickandmorty.ui.resources.R as UiR

@Composable
internal fun MainSettingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToAppearanceSettings: () -> Unit,
    onNavigateToLanguageSettings: () -> Unit,
    onNavigateToOssLicenses: () -> Unit,
    viewModel: MainSettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportDrawnWhen { !uiState.isLoading }

    MainSettingsScreen(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onUpdateUseDynamicColor = viewModel::setUseDynamicColor,
        onNavigateToAppearanceSettings = onNavigateToAppearanceSettings,
        onNavigateToLanguageSettings = onNavigateToLanguageSettings,
        onNavigateToOssLicenses = onNavigateToOssLicenses,
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MainSettingsScreen(
    uiState: MainSettingsUiState,
    onNavigateUp: () -> Unit,
    onUpdateUseDynamicColor: (Boolean) -> Unit,
    onNavigateToAppearanceSettings: () -> Unit,
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
                        theme = uiState.theme()!!,
                        applicationLocale = uiState.applicationLocale(),
                        onUpdateUseDynamicColor = onUpdateUseDynamicColor,
                        onNavigateToAppearanceSettings = onNavigateToAppearanceSettings,
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
    theme: Theme,
    applicationLocale: Locale?,
    onUpdateUseDynamicColor: (Boolean) -> Unit,
    onNavigateToAppearanceSettings: () -> Unit,
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
        key = "appearance",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(R.string.settings_appearance_title))
            },
            modifier = Modifier
                .clickable(
                    onClickLabel = stringResource(R.string.settings_appearance_tap_action),
                    onClick = onNavigateToAppearanceSettings,
                ),
            supportingContent = {
                Text(text = stringResource(theme.nightMode.label))
            },
        )
    }

    item(
        key = "dynamic_color",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(R.string.settings_dynamic_color_title))
            },
            modifier = Modifier.toggleable(
                value = theme.useDynamicColor,
                role = Role.RadioButton,
                onValueChange = onUpdateUseDynamicColor,
            ),
            trailingContent = {
                Switch(
                    checked = theme.useDynamicColor,
                    onCheckedChange = null,
                )
            },
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
            supportingContent = {
                Text(text = LocalContext.current.versionName)
            },
        )
    }
}
