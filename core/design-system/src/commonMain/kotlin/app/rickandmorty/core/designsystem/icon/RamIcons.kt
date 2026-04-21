package app.rickandmorty.core.designsystem.icon

import app.rickandmorty.core.designsystem.resources.Res
import app.rickandmorty.core.designsystem.resources.arrow_back_filled
import app.rickandmorty.core.designsystem.resources.face
import app.rickandmorty.core.designsystem.resources.face_filled
import app.rickandmorty.core.designsystem.resources.map
import app.rickandmorty.core.designsystem.resources.map_filled
import app.rickandmorty.core.designsystem.resources.settings_filled
import app.rickandmorty.core.designsystem.resources.tv
import app.rickandmorty.core.designsystem.resources.tv_filled
import org.jetbrains.compose.resources.DrawableResource

public object RamIcons {
  public object Filled {
    public val ArrowBack: DrawableResource = Res.drawable.arrow_back_filled
    public val Face: DrawableResource = Res.drawable.face_filled
    public val Map: DrawableResource = Res.drawable.map_filled
    public val Settings: DrawableResource = Res.drawable.settings_filled
    public val Tv: DrawableResource = Res.drawable.tv_filled
  }

  public object Outlined {
    public val Face: DrawableResource = Res.drawable.face
    public val Map: DrawableResource = Res.drawable.map
    public val Tv: DrawableResource = Res.drawable.tv
  }
}
