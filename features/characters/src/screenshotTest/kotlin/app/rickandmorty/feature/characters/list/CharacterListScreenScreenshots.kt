@file:SuppressLint("ComposePreviewPublic", "ComposeUnstableReceiver")

package app.rickandmorty.feature.characters.list

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import app.rickandmorty.core.designsystem.theme.RamTheme
import app.rickandmorty.data.model.Character

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
    @Preview(showBackground = true)
    @PreviewLightDark
    @Composable
    fun CharacterItemPreview() {
        RamTheme {
            CharacterItem(character = charactersPreviewData[0])
        }
    }
}
