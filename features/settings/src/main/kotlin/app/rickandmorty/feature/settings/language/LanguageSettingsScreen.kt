package app.rickandmorty.feature.settings.language

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.navigate_up
import app.rickandmorty.core.l10n.resources.settings_language_system_default
import app.rickandmorty.core.l10n.resources.settings_language_title
import app.rickandmorty.data.model.Locale
import app.rickandmorty.feature.settings.SettingsContentType
import app.rickandmorty.feature.settings.loader
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LanguageSettingsScreen(
    onNavigateUp: () -> Unit,
    viewModel: LanguageSettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportDrawnWhen { !uiState.isLoading }

    LanguageSettingsScreen(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onSelectLocale = remember(viewModel, onNavigateUp) {
            { locale ->
                viewModel.setAppLocale(locale)
                onNavigateUp()
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSettingsScreen(
    uiState: LanguageSettingsUiState,
    onNavigateUp: () -> Unit,
    onSelectLocale: (Locale?) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LanguageSettingsAppBar(
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .selectableGroup()
                .fillMaxSize()
                .consumeWindowInsets(contentPadding),
            contentPadding = contentPadding,
        ) {
            when {
                uiState.isLoading -> {
                    loader()
                }

                else -> {
                    val currentAppLocale = uiState.appLocale()

                    systemDefault(
                        currentAppLocale = currentAppLocale,
                        onSelectLocale = onSelectLocale,
                    )

                    val availableAppLocales = uiState.availableAppLocales()!!
                    availableAppLocales(
                        currentAppLocale = currentAppLocale,
                        availableAppLocales = availableAppLocales,
                        onSelectLocale = onSelectLocale,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSettingsAppBar(
    onNavigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(L10nRes.string.settings_language_title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = RamIcons.Filled.ArrowBack,
                    contentDescription = stringResource(L10nRes.string.navigate_up),
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

private fun LazyListScope.systemDefault(
    currentAppLocale: Locale?,
    onSelectLocale: (Locale?) -> Unit,
) {
    item(
        key = "system_default",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        LocaleItem(
            text = stringResource(L10nRes.string.settings_language_system_default),
            isSelected = currentAppLocale == null,
            onClick = { onSelectLocale(null) },
        )
    }
}

private fun LazyListScope.availableAppLocales(
    currentAppLocale: Locale?,
    availableAppLocales: ImmutableList<Locale>,
    onSelectLocale: (Locale?) -> Unit,
) {
    items(
        items = availableAppLocales,
        key = { locale -> locale.toLanguageTag() },
    ) { locale ->
        LocaleItem(
            text = locale.getLocalizedName(),
            isSelected = locale == currentAppLocale,
            onClick = { onSelectLocale(locale) },
        )
    }
}

@Composable
private fun LocaleItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = { Text(text = text) },
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                role = Role.RadioButton,
                onClick = onClick,
            ),
        trailingContent = {
            if (isSelected) {
                Icon(
                    imageVector = RamIcons.Filled.Check,
                    contentDescription = null,
                )
            }
        },
    )
}
