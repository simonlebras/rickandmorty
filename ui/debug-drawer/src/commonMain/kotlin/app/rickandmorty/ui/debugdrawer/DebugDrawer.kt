package app.rickandmorty.ui.debugdrawer

import androidx.compose.animation.ExperimentalLookaheadAnimationVisualDebugApi
import androidx.compose.animation.LookaheadAnimationVisualDebugging
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.designsystem.component.SettingsHeader
import app.rickandmorty.core.designsystem.component.SettingsItem
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.debug_build_information_title
import app.rickandmorty.core.l10n.resources.debug_clear_image_cache_tap_action
import app.rickandmorty.core.l10n.resources.debug_clear_image_cache_title
import app.rickandmorty.core.l10n.resources.debug_images_title
import app.rickandmorty.core.l10n.resources.debug_keyline_overlay_title
import app.rickandmorty.core.l10n.resources.debug_lookahead_debugging_title
import app.rickandmorty.core.l10n.resources.debug_package_title
import app.rickandmorty.core.l10n.resources.debug_title
import app.rickandmorty.core.l10n.resources.debug_ui_debugging_title
import app.rickandmorty.core.l10n.resources.debug_version_title
import app.rickandmorty.data.debug.DebugSettings
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DebugDrawer(
  viewModel: DebugDrawerViewModel = metroViewModel(),
  content: @Composable () -> Unit,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  DebugDrawer(
    uiState = uiState,
    onKeylineOverlayEnabledChange = viewModel::setKeylineOverlayEnabled,
    onLookaheadDebuggingEnabledChange = viewModel::setLookaheadDebuggingEnabled,
    onClearImageCache = viewModel::clearImageCache,
    content = content,
  )
}

@OptIn(ExperimentalLookaheadAnimationVisualDebugApi::class)
@Composable
private fun DebugDrawer(
  uiState: DebugDrawerUiState,
  onKeylineOverlayEnabledChange: (Boolean) -> Unit,
  onLookaheadDebuggingEnabledChange: (Boolean) -> Unit,
  onClearImageCache: () -> Unit,
  content: @Composable () -> Unit,
) {
  // The layout direction is inverted so that the drawer opens from the end edge, keeping the
  // start edge free for the app's own drag gestures.
  val originalLayoutDirection = LocalLayoutDirection.current
  val invertedLayoutDirection =
    when (originalLayoutDirection) {
      LayoutDirection.Ltr -> LayoutDirection.Rtl
      LayoutDirection.Rtl -> LayoutDirection.Ltr
    }

  CompositionLocalProvider(LocalLayoutDirection provides invertedLayoutDirection) {
    ModalNavigationDrawer(
      drawerContent = {
        CompositionLocalProvider(LocalLayoutDirection provides originalLayoutDirection) {
          DebugDrawerSheet(
            uiState = uiState,
            onKeylineOverlayEnabledChange = onKeylineOverlayEnabledChange,
            onLookaheadDebuggingEnabledChange = onLookaheadDebuggingEnabledChange,
            onClearImageCache = onClearImageCache,
          )
        }
      }
    ) {
      CompositionLocalProvider(LocalLayoutDirection provides originalLayoutDirection) {
        Box(modifier = Modifier.fillMaxSize()) {
          val settings = uiState.debugSettings()

          LookaheadAnimationVisualDebugging(
            isEnabled = settings?.lookaheadDebuggingEnabled == true
          ) {
            content()
          }

          if (settings?.keylineOverlayEnabled == true) {
            KeylineOverlay()
          }
        }
      }
    }
  }
}

@Composable
private fun DebugDrawerSheet(
  uiState: DebugDrawerUiState,
  onKeylineOverlayEnabledChange: (Boolean) -> Unit,
  onLookaheadDebuggingEnabledChange: (Boolean) -> Unit,
  onClearImageCache: () -> Unit,
) {
  ModalDrawerSheet {
    Text(
      text = stringResource(L10nRes.string.debug_title),
      style = MaterialTheme.typography.titleLarge,
      modifier = Modifier.padding(16.dp).semantics { heading() },
    )

    when {
      uiState.isLoading -> {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Loader() }
      }

      else -> {
        LazyColumn {
          buildInformation(
            versionName = uiState.versionName,
            versionCode = uiState.versionCode,
            packageName = uiState.packageName,
          )

          uiDebugging(
            settings = uiState.debugSettings()!!,
            onKeylineOverlayEnabledChange = onKeylineOverlayEnabledChange,
            onLookaheadDebuggingEnabledChange = onLookaheadDebuggingEnabledChange,
          )

          images(onClearImageCache = onClearImageCache)
        }
      }
    }
  }
}

private fun LazyListScope.buildInformation(
  versionName: String,
  versionCode: Long,
  packageName: String,
) {
  item(
    key = DebugDrawerItem.BuildInformationHeader,
    contentType = DebugDrawerContentType.Header,
  ) {
    SettingsHeader(text = stringResource(L10nRes.string.debug_build_information_title))
  }

  item(key = DebugDrawerItem.Version, contentType = DebugDrawerContentType.ListItem) {
    SettingsItem(
      headlineContent = { Text(text = stringResource(L10nRes.string.debug_version_title)) },
      supportingContent = { Text(text = "$versionName ($versionCode)") },
    )
  }

  item(key = DebugDrawerItem.Package, contentType = DebugDrawerContentType.ListItem) {
    SettingsItem(
      headlineContent = { Text(text = stringResource(L10nRes.string.debug_package_title)) },
      supportingContent = { Text(text = packageName) },
    )
  }
}

private fun LazyListScope.uiDebugging(
  settings: DebugSettings,
  onKeylineOverlayEnabledChange: (Boolean) -> Unit,
  onLookaheadDebuggingEnabledChange: (Boolean) -> Unit,
) {
  item(key = DebugDrawerItem.UiDebuggingHeader, contentType = DebugDrawerContentType.Header) {
    SettingsHeader(text = stringResource(L10nRes.string.debug_ui_debugging_title))
  }

  item(key = DebugDrawerItem.KeylineOverlay, contentType = DebugDrawerContentType.ListItem) {
    SettingsItem(
      headlineContent = { Text(text = stringResource(L10nRes.string.debug_keyline_overlay_title)) },
      modifier =
        Modifier.toggleable(
          value = settings.keylineOverlayEnabled,
          role = Role.Switch,
          onValueChange = onKeylineOverlayEnabledChange,
        ),
      trailingContent = {
        Switch(checked = settings.keylineOverlayEnabled, onCheckedChange = null)
      },
    )
  }

  item(key = DebugDrawerItem.LookaheadDebugging, contentType = DebugDrawerContentType.ListItem) {
    SettingsItem(
      headlineContent = {
        Text(text = stringResource(L10nRes.string.debug_lookahead_debugging_title))
      },
      modifier =
        Modifier.toggleable(
          value = settings.lookaheadDebuggingEnabled,
          role = Role.Switch,
          onValueChange = onLookaheadDebuggingEnabledChange,
        ),
      trailingContent = {
        Switch(checked = settings.lookaheadDebuggingEnabled, onCheckedChange = null)
      },
    )
  }
}

private fun LazyListScope.images(onClearImageCache: () -> Unit) {
  item(key = DebugDrawerItem.ImagesHeader, contentType = DebugDrawerContentType.Header) {
    SettingsHeader(text = stringResource(L10nRes.string.debug_images_title))
  }

  item(key = DebugDrawerItem.ClearImageCache, contentType = DebugDrawerContentType.ListItem) {
    SettingsItem(
      headlineContent = {
        Text(text = stringResource(L10nRes.string.debug_clear_image_cache_title))
      },
      modifier =
        Modifier.clickable(
          onClickLabel = stringResource(L10nRes.string.debug_clear_image_cache_tap_action),
          onClick = onClearImageCache,
        ),
    )
  }
}
