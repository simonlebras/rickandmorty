@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package app.rickandmorty.ui.settings.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.designsystem.component.BackNavButton
import app.rickandmorty.core.designsystem.theme.isDynamicColorAvailable
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_about_title
import app.rickandmorty.core.l10n.resources.settings_app_version_title
import app.rickandmorty.core.l10n.resources.settings_dynamic_color_title
import app.rickandmorty.core.l10n.resources.settings_general_title
import app.rickandmorty.core.l10n.resources.settings_language_system_default
import app.rickandmorty.core.l10n.resources.settings_language_tap_action
import app.rickandmorty.core.l10n.resources.settings_language_title
import app.rickandmorty.core.l10n.resources.settings_license_summary
import app.rickandmorty.core.l10n.resources.settings_license_tap_action
import app.rickandmorty.core.l10n.resources.settings_license_title
import app.rickandmorty.core.l10n.resources.settings_theme_tap_action
import app.rickandmorty.core.l10n.resources.settings_theme_title
import app.rickandmorty.core.l10n.resources.settings_title
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.data.locale.Locale
import app.rickandmorty.data.theme.Theme
import app.rickandmorty.ui.settings.common.Header
import app.rickandmorty.ui.settings.common.SettingsContentType
import app.rickandmorty.ui.settings.common.loader
import app.rickandmorty.ui.settings.common.util.label
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MainSettingsScreen(
  selectedItem: MainSettingsItem?,
  onNavigateUp: () -> Unit,
  onNavigateToThemeSettings: () -> Unit,
  onNavigateToLanguageSettings: () -> Unit,
  onNavigateToLicenseSettings: () -> Unit,
  viewModel: MainSettingsViewModel = metroViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  ReportDrawnWhen { !uiState.isLoading }

  MainSettingsScreen(
    uiState = uiState,
    selectedItem = selectedItem,
    onNavigateUp = onNavigateUp,
    onUpdateUseDynamicColor = viewModel::setUseDynamicColor,
    onNavigateToThemeSettings = onNavigateToThemeSettings,
    onNavigateToLanguageSettings = onNavigateToLanguageSettings,
    onNavigateToLicenseSettings = onNavigateToLicenseSettings,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSettingsScreen(
  uiState: MainSettingsUiState,
  selectedItem: MainSettingsItem?,
  onNavigateUp: () -> Unit,
  onUpdateUseDynamicColor: (Boolean) -> Unit,
  onNavigateToThemeSettings: () -> Unit,
  onNavigateToLanguageSettings: () -> Unit,
  onNavigateToLicenseSettings: () -> Unit,
  isDynamicColorAvailable: Boolean = isDynamicColorAvailable(),
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  HazeScaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = { MainSettingsAppBar(onNavigateUp = onNavigateUp, scrollBehavior = scrollBehavior) },
  ) { contentPadding ->
    LazyColumn(
      modifier = Modifier.fillMaxSize().consumeWindowInsets(contentPadding),
      contentPadding = contentPadding,
    ) {
      when {
        uiState.isLoading -> {
          loader()
        }

        else -> {
          generalSettings(
            selectedItem = selectedItem,
            currentTheme = uiState.theme()!!,
            currentAppLocale = uiState.appLocale(),
            isDynamicColorAvailable = isDynamicColorAvailable,
            onUpdateUseDynamicColor = onUpdateUseDynamicColor,
            onNavigateToThemeSettings = onNavigateToThemeSettings,
            onNavigateToLanguageSettings = onNavigateToLanguageSettings,
          )

          aboutSettings(
            selectedItem = selectedItem,
            versionName = uiState.versionName,
            onNavigateToLicenseSettings = onNavigateToLicenseSettings,
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSettingsAppBar(onNavigateUp: () -> Unit, scrollBehavior: TopAppBarScrollBehavior) {
  CenterAlignedTopAppBar(
    title = { Text(text = stringResource(L10nRes.string.settings_title)) },
    navigationIcon = { BackNavButton(onClick = onNavigateUp) },
    colors =
      TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent,
      ),
    scrollBehavior = scrollBehavior,
  )
}

private fun LazyListScope.generalSettings(
  selectedItem: MainSettingsItem?,
  currentTheme: Theme,
  currentAppLocale: Locale?,
  isDynamicColorAvailable: Boolean,
  onUpdateUseDynamicColor: (Boolean) -> Unit,
  onNavigateToThemeSettings: () -> Unit,
  onNavigateToLanguageSettings: () -> Unit,
) {
  item(key = MainSettingsItem.GeneralHeader, contentType = SettingsContentType.HEADER) {
    Header(
      text = stringResource(L10nRes.string.settings_general_title),
      modifier = Modifier.fillMaxWidth(),
    )
  }

  item(key = MainSettingsItem.Theme, contentType = SettingsContentType.LIST_ITEM) {
    ListItem(
      headlineContent = { Text(text = stringResource(L10nRes.string.settings_theme_title)) },
      modifier =
        Modifier.clickable(
          onClickLabel = stringResource(L10nRes.string.settings_theme_tap_action),
          onClick = onNavigateToThemeSettings,
        ),
      supportingContent = { Text(text = stringResource(currentTheme.nightMode.label)) },
    )
  }

  if (isDynamicColorAvailable) {
    item(key = MainSettingsItem.DynamicColor, contentType = SettingsContentType.LIST_ITEM) {
      ListItem(
        headlineContent = {
          Text(text = stringResource(L10nRes.string.settings_dynamic_color_title))
        },
        modifier =
          Modifier.toggleable(
            value = currentTheme.useDynamicColor,
            role = Role.RadioButton,
            onValueChange = onUpdateUseDynamicColor,
          ),
        trailingContent = { Switch(checked = currentTheme.useDynamicColor, onCheckedChange = null) },
      )
    }
  }

  item(key = MainSettingsItem.Language, contentType = SettingsContentType.LIST_ITEM) {
    val clickLabel = stringResource(L10nRes.string.settings_language_tap_action)
    ListItem(
      selected = selectedItem == MainSettingsItem.Language,
      onClick = onNavigateToLanguageSettings,
      modifier =
        Modifier.semantics {
          onClick(label = clickLabel) {
            onNavigateToLanguageSettings()
            true
          }
        },
      supportingContent = {
        val localeName =
          currentAppLocale?.getLocalizedName()
            ?: stringResource(L10nRes.string.settings_language_system_default)
        Text(text = localeName)
      },
    ) {
      Text(text = stringResource(L10nRes.string.settings_language_title))
    }
  }
}

private fun LazyListScope.aboutSettings(
  selectedItem: MainSettingsItem?,
  versionName: String,
  onNavigateToLicenseSettings: () -> Unit,
) {
  item(key = MainSettingsItem.AboutHeader, contentType = SettingsContentType.HEADER) {
    Header(
      text = stringResource(L10nRes.string.settings_about_title),
      modifier = Modifier.fillMaxWidth(),
    )
  }

  item(key = MainSettingsItem.Licenses, contentType = SettingsContentType.LIST_ITEM) {
    val clickLabel = stringResource(L10nRes.string.settings_license_tap_action)
    ListItem(
      selected = selectedItem == MainSettingsItem.Licenses,
      onClick = onNavigateToLicenseSettings,
      modifier =
        Modifier.semantics {
          onClick(label = clickLabel) {
            onNavigateToLicenseSettings()
            true
          }
        },
      supportingContent = { Text(text = stringResource(L10nRes.string.settings_license_summary)) },
    ) {
      Text(text = stringResource(L10nRes.string.settings_license_title))
    }
  }

  item(key = MainSettingsItem.AppVersion, contentType = SettingsContentType.LIST_ITEM) {
    ListItem(
      headlineContent = { Text(text = stringResource(L10nRes.string.settings_app_version_title)) },
      supportingContent = { Text(text = versionName) },
    )
  }
}
