package app.rickandmorty.core.designsystem.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun Loader(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}
