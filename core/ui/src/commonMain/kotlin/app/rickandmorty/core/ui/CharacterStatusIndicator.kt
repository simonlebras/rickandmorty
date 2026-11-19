package app.rickandmorty.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.rickandmorty.data.character.Character.Status

@Composable
public fun CharacterStatusIndicator(status: Status, modifier: Modifier = Modifier) {
  val color =
    when (status) {
      Status.Alive -> Color.Green
      Status.Dead -> Color.Red
      else -> Color.Gray
    }
  Spacer(modifier = modifier.size(16.dp).background(color = color, shape = CircleShape))
}
