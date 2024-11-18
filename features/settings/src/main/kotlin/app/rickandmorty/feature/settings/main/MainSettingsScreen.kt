package app.rickandmorty.feature.settings.main

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.theme.supportsDynamicTheming
import app.rickandmorty.core.l10n.R as L10nR
import app.rickandmorty.data.model.Locale
import app.rickandmorty.data.model.Theme
import app.rickandmorty.feature.settings.Header
import app.rickandmorty.feature.settings.SettingsContentType
import app.rickandmorty.feature.settings.loader
import app.rickandmorty.feature.settings.util.label
import app.rickandmorty.feature.settings.util.versionName

@Composable
internal fun MainSettingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToThemeSettings: () -> Unit,
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
        onNavigateToThemeSettings = onNavigateToThemeSettings,
        onNavigateToLanguageSettings = onNavigateToLanguageSettings,
        onNavigateToOssLicenses = onNavigateToOssLicenses,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSettingsScreen(
    uiState: MainSettingsUiState,
    onNavigateUp: () -> Unit,
    onUpdateUseDynamicColor: (Boolean) -> Unit,
    onNavigateToThemeSettings: () -> Unit,
    onNavigateToLanguageSettings: () -> Unit,
    onNavigateToOssLicenses: () -> Unit,
    supportsDynamicTheming: Boolean = supportsDynamicTheming(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MainSettingsAppBar(
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(contentPadding),
            contentPadding = contentPadding,
        ) {
            when {
                uiState.isLoading -> {
                    loader()
                }

                else -> {
                    generalSettings(
                        currentTheme = uiState.theme()!!,
                        currentAppLocale = uiState.appLocale(),
                        supportsDynamicTheming = supportsDynamicTheming,
                        onUpdateUseDynamicColor = onUpdateUseDynamicColor,
                        onNavigateToThemeSettings = onNavigateToThemeSettings,
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
            Text(text = stringResource(L10nR.string.settings_title))
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

private fun LazyListScope.generalSettings(
    currentTheme: Theme,
    currentAppLocale: Locale?,
    supportsDynamicTheming: Boolean,
    onUpdateUseDynamicColor: (Boolean) -> Unit,
    onNavigateToThemeSettings: () -> Unit,
    onNavigateToLanguageSettings: () -> Unit,
) {
    item(
        key = "general",
        contentType = SettingsContentType.HEADER,
    ) {
        Header(
            text = stringResource(L10nR.string.settings_general_title),
            modifier = Modifier.fillMaxWidth(),
        )
    }

    item(
        key = "theme",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(L10nR.string.settings_theme_title))
            },
            modifier = Modifier
                .clickable(
                    onClickLabel = stringResource(L10nR.string.settings_theme_tap_action),
                    onClick = onNavigateToThemeSettings,
                ),
            supportingContent = {
                Text(text = stringResource(currentTheme.nightMode.label))
            },
        )
    }

    if (supportsDynamicTheming) {
        item(
            key = "dynamic_color",
            contentType = SettingsContentType.LIST_ITEM,
        ) {
            ListItem(
                headlineContent = {
                    Text(text = stringResource(L10nR.string.settings_dynamic_color_title))
                },
                modifier = Modifier.toggleable(
                    value = currentTheme.useDynamicColor,
                    role = Role.RadioButton,
                    onValueChange = onUpdateUseDynamicColor,
                ),
                trailingContent = {
                    Switch(
                        checked = currentTheme.useDynamicColor,
                        onCheckedChange = null,
                    )
                },
            )
        }
    }

    item(
        key = "language",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(L10nR.string.settings_language_title))
            },
            modifier = Modifier
                .clickable(
                    onClickLabel = stringResource(L10nR.string.settings_language_tap_action),
                    onClick = onNavigateToLanguageSettings,
                ),
            supportingContent = {
                val localeName = currentAppLocale?.getLocalizedName()
                    ?: stringResource(L10nR.string.settings_language_system_default)
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
            text = stringResource(L10nR.string.settings_about_title),
            modifier = Modifier.fillMaxWidth(),
        )
    }

    item(
        key = "oss_licenses",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(L10nR.string.settings_oss_licenses_title))
            },
            modifier = Modifier
                .clickable(
                    onClickLabel = stringResource(L10nR.string.settings_oss_licenses_tap_action),
                    onClick = onNavigateToOssLicenses,
                ),
            supportingContent = {
                Text(text = stringResource(L10nR.string.settings_oss_licenses_summary))
            },
        )
    }

    item(
        key = "app_version",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(L10nR.string.settings_app_version_title))
            },
            supportingContent = {
                Text(text = LocalContext.current.versionName)
            },
        )
    }
}
