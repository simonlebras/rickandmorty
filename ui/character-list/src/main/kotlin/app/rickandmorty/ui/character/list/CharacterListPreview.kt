package app.rickandmorty.ui.character.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import app.rickandmorty.data.character.Character
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler

internal class CharacterPreviewParameterProvider : PreviewParameterProvider<Character> {
  override val values: Sequence<Character> =
    sequenceOf(
      Character(
        id = "1",
        name = "Rick Sanchez",
        status = Character.Status.Alive,
        species = Character.Species.Human,
        type = "",
        gender = Character.Gender.Male,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
      )
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun ProvideCharacterImagePreviewHandler(content: @Composable () -> Unit) {
  val image = ImageBitmap.imageResource(R.drawable.rick).asAndroidBitmap().asImage()
  val previewHandler = AsyncImagePreviewHandler { image }
  CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler, content = content)
}
