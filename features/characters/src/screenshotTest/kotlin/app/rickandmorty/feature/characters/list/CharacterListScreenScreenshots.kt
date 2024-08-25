@file:SuppressLint("ComposePreviewPublic", "ComposeUnstableReceiver")

package app.rickandmorty.feature.characters.list

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import app.rickandmorty.core.ui.PreviewWrapper
import app.rickandmorty.data.model.Character

class CharacterListScreenScreenshots {
    @Preview(showBackground = true)
    @PreviewLightDark
    @Composable
    fun CharacterItemPreview(
        @PreviewParameter(CharacterPreviewParameterProvider::class) character: Character,
    ) {
        PreviewWrapper {
            ProvideCharacterImagePreviewHandler {
                CharacterItem(character = character)
            }
        }
    }
}
