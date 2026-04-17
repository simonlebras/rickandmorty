package app.rickandmorty.ui.settings.license

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.designsystem.component.BackNavButton
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_license_title
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.data.license.License
import app.rickandmorty.ui.settings.common.loader
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LicenseSettingsScreen(
  onNavigateUp: () -> Unit,
  viewModel: LicenseSettingsViewModel = metroViewModel(),
  showBackButton: Boolean = true,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  ReportDrawnWhen { !uiState.isLoading }

  LicenseSettingsScreen(
    uiState = uiState,
    onNavigateUp = onNavigateUp,
    showBackButton = showBackButton,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LicenseSettingsScreen(
  uiState: LicenseSettingsUiState,
  onNavigateUp: () -> Unit,
  showBackButton: Boolean,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  HazeScaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      LicenseSettingsAppBar(
        onNavigateUp = onNavigateUp,
        showBackButton = showBackButton,
        scrollBehavior = scrollBehavior,
      )
    },
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
          val licenses = uiState.licenses()!!
          items(licenses.size) { index ->
            val license = licenses[index]
            LicenseItem(license = license)
          }
        }
      }
    }
  }
}

@Composable
private fun LicenseItem(license: License) {
  ListItem(
    headlineContent = { Text(text = license.artifactId) },
    supportingContent = { Text(text = license.groupId) },
    trailingContent = { Text(text = license.version) },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LicenseSettingsAppBar(
  onNavigateUp: () -> Unit,
  showBackButton: Boolean,
  scrollBehavior: TopAppBarScrollBehavior,
) {
  CenterAlignedTopAppBar(
    title = { Text(text = stringResource(L10nRes.string.settings_license_title)) },
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
