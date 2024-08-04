@file:SuppressLint("ComposePreviewPublic", "ComposeUnstableReceiver")

package app.rickandmorty.feature.characters.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import app.rickandmorty.core.designsystem.theme.RamTheme
import app.rickandmorty.data.model.Character
import app.rickandmorty.feature.characters.R
import coil3.ImageDrawable
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler

private val charactersPreviewData = listOf(
    Character(
        id = "1",
        name = "Rick Sanchez",
        status = Character.Status.Alive,
        species = Character.Species.Human,
        type = "",
        gender = Character.Gender.Male,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
    ),
)

class CharacterListScreenScreenshots {
    @OptIn(ExperimentalCoilApi::class)
    @Preview(showBackground = true)
    @PreviewLightDark
    @Composable
    fun CharacterItemPreview() {
        val image =  ImageBitmap.imageResource(R.drawable.rick).asAndroidBitmap().asImage()
        val previewHandler = AsyncImagePreviewHandler {
            image
        }
        
        CompositionLocalProvider(
            LocalAsyncImagePreviewHandler provides previewHandler
        ) {
            RamTheme {
                CharacterItem(character = charactersPreviewData[0])
            }
        }
    }
}
