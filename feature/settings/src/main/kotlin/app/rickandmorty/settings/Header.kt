package app.rickandmorty.settings

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun Header(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .semantics { heading() }
            .defaultMinSize(minHeight = 32.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 8.dp,
                end = 24.dp,
                bottom = 8.dp,
            ),
            style = MaterialTheme.typography.titleSmall,
        )
    }
}
