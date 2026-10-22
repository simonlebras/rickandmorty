package app.rickandmorty.ui.settings.language

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.CenterAlignedTopAppBar
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
import app.rickandmorty.core.designsystem.component.BackNavButton
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_language_title
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.data.locale.AppLocale
import app.rickandmorty.ui.settings.common.SettingsContentType
import app.rickandmorty.ui.settings.common.util.label
import com.tunjid.mutator.compose.produceStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LanguageSettingsScreen(
  onNavigateUp: () -> Unit,
  showBackButton: Boolean = true,
  stateHolder: LanguageSettingsStateHolder = metroViewModel<LanguageSettingsViewModel>(),
) {
  val uiState = stateHolder.produceStateWithLifecycle()

  ReportDrawnWhen { !uiState.isLoading }

  LanguageSettingsScreen(
    uiState = uiState,
    onNavigateUp = onNavigateUp,
    showBackButton = showBackButton,
    actions = stateHolder.accept,
  )
}

@Composable
private fun LanguageSettingsScreen(
  uiState: LanguageSettingsUiState,
  onNavigateUp: () -> Unit,
  showBackButton: Boolean,
  actions: (LanguageSettingsAction) -> Unit,
) {
  val onSelectLocale: (AppLocale) -> Unit = { appLocale ->
    actions(LanguageSettingsAction.SelectLocale(appLocale))
    onNavigateUp()
  }

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
    val appLocale = uiState.appLocale
    val availableAppLocales = uiState.availableAppLocales
    when {
      appLocale == null || availableAppLocales == null -> {
        Loader(modifier = Modifier.fillMaxSize().wrapContentSize().padding(contentPadding))
      }

      else -> {
        LazyColumn(
          modifier = Modifier.fillMaxSize().selectableGroup(),
          contentPadding = contentPadding,
        ) {
          items(
            items = availableAppLocales,
            key = AppLocale::key,
            contentType = { SettingsContentType.ListItem },
          ) { option ->
            LocaleItem(
              text = option.label(),
              selected = option == appLocale,
              onClick = { onSelectLocale(option) },
            )
          }
        }
      }
    }
  }
}

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

@Composable
private fun LocaleItem(text: String, selected: Boolean, onClick: () -> Unit) {
  ListItem(
    modifier =
      Modifier.fillMaxWidth()
        .selectable(selected = selected, role = Role.RadioButton, onClick = onClick),
    leadingContent = { RadioButton(selected = selected, onClick = null) },
  ) {
    Text(text = text)
  }
}

private val AppLocale.key: String
  get() =
    when (this) {
      AppLocale.System -> "system"
      is AppLocale.Specific -> languageTag
    }
