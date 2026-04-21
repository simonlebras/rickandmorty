package app.rickandmorty.ui.settings.language

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.designsystem.component.BackNavButton
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_language_system_default
import app.rickandmorty.core.l10n.resources.settings_language_title
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.data.locale.Locale
import app.rickandmorty.ui.settings.common.SettingsContentType
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LanguageSettingsScreen(
  onNavigateUp: () -> Unit,
  viewModel: LanguageSettingsViewModel = metroViewModel(),
  showBackButton: Boolean = true,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  ReportDrawnWhen { !uiState.isLoading }

  LanguageSettingsScreen(
    uiState = uiState,
    onNavigateUp = onNavigateUp,
    showBackButton = showBackButton,
    onSelectLocale = { locale ->
      viewModel.setAppLocale(locale)
      onNavigateUp()
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSettingsScreen(
  uiState: LanguageSettingsUiState,
  onNavigateUp: () -> Unit,
  showBackButton: Boolean,
  onSelectLocale: (Locale?) -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  HazeScaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      LanguageSettingsAppBar(
        onNavigateUp = onNavigateUp,
        showBackButton = showBackButton,
        scrollBehavior = scrollBehavior,
      )
    },
  ) { contentPadding ->
    when {
      uiState.isLoading -> {
        CircularProgressIndicator(
          modifier = Modifier.fillMaxSize().wrapContentSize().padding(contentPadding)
        )
      }

      else -> {
        LazyColumn(
          modifier = Modifier.fillMaxSize().selectableGroup(),
          contentPadding = contentPadding,
        ) {
          val currentAppLocale = uiState.appLocale()

          systemDefault(currentAppLocale = currentAppLocale, onSelectLocale = onSelectLocale)

          val availableAppLocales = uiState.availableAppLocales()!!
          items(items = availableAppLocales, key = { locale -> locale.toLanguageTag() }) { locale ->
            LocaleItem(
              text = locale.getLocalizedName(),
              selected = locale == currentAppLocale,
              onClick = { onSelectLocale(locale) },
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSettingsAppBar(
  onNavigateUp: () -> Unit,
  showBackButton: Boolean,
  scrollBehavior: TopAppBarScrollBehavior,
) {
  CenterAlignedTopAppBar(
    title = { Text(text = stringResource(L10nRes.string.settings_language_title)) },
    navigationIcon = {
      if (showBackButton) {
        BackNavButton(onClick = onNavigateUp)
      }
    },
    colors =
      TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent,
      ),
    scrollBehavior = scrollBehavior,
  )
}

private fun LazyListScope.systemDefault(
  currentAppLocale: Locale?,
  onSelectLocale: (Locale?) -> Unit,
) {
  item(key = "system_default", contentType = SettingsContentType.LIST_ITEM) {
    LocaleItem(
      text = stringResource(L10nRes.string.settings_language_system_default),
      selected = currentAppLocale == null,
      onClick = { onSelectLocale(null) },
    )
  }
}

@Composable
private fun LocaleItem(text: String, selected: Boolean, onClick: () -> Unit) {
  ListItem(
    headlineContent = { Text(text = text) },
    modifier =
      Modifier.fillMaxWidth()
        .selectable(selected = selected, role = Role.RadioButton, onClick = onClick),
    leadingContent = { RadioButton(selected = selected, onClick = null) },
  )
}
