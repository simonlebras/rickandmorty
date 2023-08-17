package app.rickandmorty.feature.settings.language

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.designsystem.icon.RamIcons
import app.rickandmorty.feature.settings.R
import app.rickandmorty.feature.settings.SettingsContentType
import app.rickandmorty.feature.settings.loader
import app.rickandmorty.jankstats.TrackScrollJank
import app.rickandmorty.locale.domain.Locale
import app.rickandmorty.ui.resources.R as UiR
import kotlinx.collections.immutable.ImmutableList

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
            {
                    locale ->
                viewModel.setApplicationLocale(locale)
                onNavigateUp()
            }
        },
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSettingsScreen(
    uiState: LanguageSettingsUiState,
    onNavigateUp: () -> Unit,
    onSelectLocale: (Locale?) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            LanguageSettingsAppBar(
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { padding ->
        val listState = rememberLazyListState()

        TrackScrollJank(
            scrollableState = listState,
            stateName = "languageSettings:screen",
        )

        LazyColumn(
            modifier = Modifier
                .selectableGroup()
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
                    val currentApplicationLocale = uiState.applicationLocale()

                    systemDefault(
                        currentApplicationLocale = currentApplicationLocale,
                        onSelectLocale = onSelectLocale,
                    )

                    val availableApplicationLocales = uiState.availableApplicationLocales()!!
                    availableApplicationLocales(
                        currentApplicationLocale = currentApplicationLocale,
                        availableApplicationLocales = availableApplicationLocales,
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
            Text(text = stringResource(R.string.settings_language_title))
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

private fun LazyListScope.systemDefault(
    currentApplicationLocale: Locale?,
    onSelectLocale: (Locale?) -> Unit,
) {
    item(
        key = "system_default",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        LocaleItem(
            text = stringResource(R.string.settings_language_system_default),
            isSelected = currentApplicationLocale == null,
            onClick = { onSelectLocale(null) },
        )
    }
}

private fun LazyListScope.availableApplicationLocales(
    currentApplicationLocale: Locale?,
    availableApplicationLocales: ImmutableList<Locale>,
    onSelectLocale: (Locale?) -> Unit,
) {
    items(
        items = availableApplicationLocales,
        key = { locale -> locale.toLanguageTag() },
    ) { locale ->
        LocaleItem(
            text = locale.getDisplayName(locale),
            isSelected = locale == currentApplicationLocale,
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
