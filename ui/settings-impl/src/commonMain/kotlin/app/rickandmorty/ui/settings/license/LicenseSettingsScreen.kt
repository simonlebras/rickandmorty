package app.rickandmorty.ui.settings.license

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.designsystem.component.BackNavButton
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_license_item_tap_action
import app.rickandmorty.core.l10n.resources.settings_license_title
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.data.license.License
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LicenseSettingsScreen(
  onNavigateUp: () -> Unit,
  showBackButton: Boolean = true,
  viewModel: LicenseSettingsViewModel = metroViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  ReportDrawnWhen { !uiState.isLoading }

  val uriHandler = LocalUriHandler.current

  LicenseSettingsScreen(
    uiState = uiState,
    showBackButton = showBackButton,
    onNavigateUp = onNavigateUp,
    onLicenseClick = { license -> license.url?.let(uriHandler::openUri) },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LicenseSettingsScreen(
  uiState: LicenseSettingsUiState,
  showBackButton: Boolean,
  onNavigateUp: () -> Unit,
  onLicenseClick: (License) -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  HazeScaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      LicenseSettingsAppBar(
        showBackButton = showBackButton,
        onNavigateUp = onNavigateUp,
        scrollBehavior = scrollBehavior,
      )
    },
  ) { contentPadding ->
    when {
      uiState.isLoading -> {
        Loader(modifier = Modifier.fillMaxSize().wrapContentSize().padding(contentPadding))
      }

      else -> {
        LazyColumn(
          modifier = Modifier.fillMaxSize().consumeWindowInsets(contentPadding),
          contentPadding = contentPadding,
        ) {
          val licenses = uiState.licenses()!!
          items(licenses, key = License::uniqueId) { license ->
            LicenseItem(license = license, onClick = { onLicenseClick(license) })
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LicenseItem(license: License, onClick: () -> Unit) {
  ListItem(
    headlineContent = { Text(text = license.name) },
    modifier =
      Modifier.clickable(
        enabled = license.url != null,
        onClickLabel = stringResource(L10nRes.string.settings_license_item_tap_action),
        onClick = onClick,
      ),
    supportingContent = {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        license.author?.let { author -> Text(text = author) }

        if (license.spdxIds.isNotEmpty()) {
          FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            license.spdxIds.forEach { spdxId ->
              LibraryChip(
                text = spdxId,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
              )
            }
          }
        }
      }
    },
    trailingContent = {
      LibraryChip(
        text = license.version,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    },
  )
}

@Composable
private fun LibraryChip(text: String, containerColor: Color, contentColor: Color) {
  Surface(color = containerColor, contentColor = contentColor, shape = MaterialTheme.shapes.small) {
    Text(
      text = text,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
      style = MaterialTheme.typography.labelSmall,
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LicenseSettingsAppBar(
  showBackButton: Boolean,
  onNavigateUp: () -> Unit,
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
