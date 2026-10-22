@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package app.rickandmorty.ui.settings.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import app.rickandmorty.core.designsystem.component.BackNavButton
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.designsystem.component.SettingsHeader
import app.rickandmorty.core.designsystem.component.SettingsItem
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.theme.isDynamicColorAvailable
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_about_title
import app.rickandmorty.core.l10n.resources.settings_app_version_copy_tap_action
import app.rickandmorty.core.l10n.resources.settings_app_version_title
import app.rickandmorty.core.l10n.resources.settings_dynamic_color_title
import app.rickandmorty.core.l10n.resources.settings_general_title
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
import app.rickandmorty.data.locale.AppLocale
import app.rickandmorty.data.theme.Theme
import app.rickandmorty.ui.settings.common.SettingsContentType
import app.rickandmorty.ui.settings.common.util.label
import app.rickandmorty.ui.settings.common.util.setPlainText
import app.rickandmorty.ui.settings.theme.ThemeSettingsDialog
import com.tunjid.mutator.compose.produceStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MainSettingsScreen(
  selectedItem: MainSettingsItem?,
  onNavigateUp: () -> Unit,
  onNavigateToLanguageSettings: () -> Unit,
  onNavigateToLicenseSettings: () -> Unit,
  stateHolder: MainSettingsStateHolder = metroViewModel<MainSettingsViewModel>(),
) {
  val uiState = stateHolder.produceStateWithLifecycle()

  var showThemeSettingsDialog by rememberSaveable { mutableStateOf(false) }

  ReportDrawnWhen { !uiState.isLoading }

  MainSettingsScreen(
    uiState = uiState,
    selectedItem = selectedItem,
    onNavigateUp = onNavigateUp,
    actions = stateHolder.accept,
    onNavigateToThemeSettings = { showThemeSettingsDialog = true },
    onNavigateToLanguageSettings = onNavigateToLanguageSettings,
    onNavigateToLicenseSettings = onNavigateToLicenseSettings,
  )

  if (showThemeSettingsDialog) {
    ThemeSettingsDialog(onDismiss = { showThemeSettingsDialog = false })
  }
}

@Composable
private fun MainSettingsScreen(
  uiState: MainSettingsUiState,
  selectedItem: MainSettingsItem?,
  onNavigateUp: () -> Unit,
  actions: (MainSettingsAction) -> Unit,
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
    val theme = uiState.theme
    val appLocale = uiState.appLocale
    when {
      theme == null || appLocale == null -> {
        Loader(modifier = Modifier.fillMaxSize().wrapContentSize().padding(contentPadding))
      }

      else -> {
        LazyColumn(
          modifier = Modifier.fillMaxSize().consumeWindowInsets(contentPadding),
          contentPadding = contentPadding,
        ) {
          generalSettings(
            selectedItem = selectedItem,
            currentTheme = theme,
            currentAppLocale = appLocale,
            isDynamicColorAvailable = isDynamicColorAvailable,
            onUpdateUseDynamicColor = { useDynamicColor ->
              actions(MainSettingsAction.SetUseDynamicColor(useDynamicColor))
            },
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
  currentAppLocale: AppLocale,
  isDynamicColorAvailable: Boolean,
  onUpdateUseDynamicColor: (Boolean) -> Unit,
  onNavigateToThemeSettings: () -> Unit,
  onNavigateToLanguageSettings: () -> Unit,
) {
  item(key = MainSettingsItem.GeneralHeader, contentType = SettingsContentType.Header) {
    SettingsHeader(
      text = stringResource(L10nRes.string.settings_general_title),
      modifier = Modifier.fillMaxWidth(),
    )
  }

  item(key = MainSettingsItem.Theme, contentType = SettingsContentType.ListItem) {
    SettingsItem(
      modifier =
        Modifier.clickable(
          onClickLabel = stringResource(L10nRes.string.settings_theme_tap_action),
          onClick = onNavigateToThemeSettings,
        ),
      supportingContent = { Text(text = stringResource(currentTheme.nightMode.label)) },
    ) {
      Text(text = stringResource(L10nRes.string.settings_theme_title))
    }
  }

  if (isDynamicColorAvailable) {
    item(key = MainSettingsItem.DynamicColor, contentType = SettingsContentType.ListItem) {
      SettingsItem(
        modifier =
          Modifier.toggleable(
            value = currentTheme.useDynamicColor,
            role = Role.RadioButton,
            onValueChange = onUpdateUseDynamicColor,
          ),
        trailingContent = {
          Switch(checked = currentTheme.useDynamicColor, onCheckedChange = null)
        },
      ) {
        Text(text = stringResource(L10nRes.string.settings_dynamic_color_title))
      }
    }
  }

  item(key = MainSettingsItem.Language, contentType = SettingsContentType.ListItem) {
    val clickLabel = stringResource(L10nRes.string.settings_language_tap_action)
    SettingsItem(
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
        Text(text = currentAppLocale.label())
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
  item(key = MainSettingsItem.AboutHeader, contentType = SettingsContentType.Header) {
    SettingsHeader(
      text = stringResource(L10nRes.string.settings_about_title),
      modifier = Modifier.fillMaxWidth(),
    )
  }

  item(key = MainSettingsItem.Licenses, contentType = SettingsContentType.ListItem) {
    val clickLabel = stringResource(L10nRes.string.settings_license_tap_action)
    SettingsItem(
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

  item(key = MainSettingsItem.AppVersion, contentType = SettingsContentType.ListItem) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val title = stringResource(L10nRes.string.settings_app_version_title)

    SettingsItem(
      modifier =
        Modifier.clickable(
          onClickLabel = stringResource(L10nRes.string.settings_app_version_copy_tap_action),
          onClick = { scope.launch { clipboard.setPlainText(label = title, text = versionName) } },
        ),
      supportingContent = { Text(text = versionName) },
      trailingContent = {
        Icon(
          painter = painterResource(RamIcons.Outlined.ContentCopy),
          contentDescription = null,
        )
      },
    ) {
      Text(text = title)
    }
  }
}
