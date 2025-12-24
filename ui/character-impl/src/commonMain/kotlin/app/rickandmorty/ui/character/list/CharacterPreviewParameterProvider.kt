package app.rickandmorty.ui.character.list

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import app.rickandmorty.data.character.Character

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
