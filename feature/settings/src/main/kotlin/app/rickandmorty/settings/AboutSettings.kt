package app.rickandmorty.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import app.rickandmorty.settings.utils.versionName

internal fun LazyListScope.aboutSettings(
    onNavigateToOssLicenses: () -> Unit,
) {
    item(
        key = "about",
        contentType = SettingsContentType.HEADER,
    ) {
        Header(text = stringResource(R.string.settings_about_title))
    }

    item(
        key = "oss_licenses",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(R.string.settings_oss_licenses_title))
            },
            modifier = Modifier.clickable(
                onClickLabel = stringResource(R.string.settings_oss_licenses_tap_action),
                onClick = onNavigateToOssLicenses,
            ),
            supportingContent = {
                Text(text = stringResource(R.string.settings_oss_licenses_summary))
            },
        )
    }

    item(
        key = "app_version",
        contentType = SettingsContentType.LIST_ITEM,
    ) {
        ListItem(
            headlineContent = {
                Text(text = stringResource(R.string.settings_app_version))
            },
            supportingContent = {
                Text(text = LocalContext.current.versionName)
            },
        )
    }
}
