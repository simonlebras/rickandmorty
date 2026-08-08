package app.rickandmorty.ui.settings.license

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.designsystem.component.BackNavButton
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_license_item_tap_action
import app.rickandmorty.core.l10n.resources.settings_license_title
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.data.license.License
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.painterResource
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
          items(licenses.size) { index ->
            val license = licenses[index]
            LicenseItem(license = license, onClick = { onLicenseClick(license) })
          }
        }
      }
    }
  }
}

@Composable
private fun LicenseItem(license: License, onClick: () -> Unit) {
  val hasUrl = license.url != null

  ListItem(
    headlineContent = { Text(text = license.artifactId) },
    modifier =
      if (hasUrl) {
        Modifier.clickable(
          onClickLabel = stringResource(L10nRes.string.settings_license_item_tap_action),
          onClick = onClick,
        )
      } else {
        Modifier
      },
    overlineContent = { Text(text = license.groupId) },
    supportingContent = { Text(text = license.version) },
    trailingContent =
      if (hasUrl) {
        {
          Icon(
            painter = painterResource(RamIcons.Outlined.OpenInNew),
            contentDescription = null,
          )
        }
      } else {
        null
      },
  )
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
