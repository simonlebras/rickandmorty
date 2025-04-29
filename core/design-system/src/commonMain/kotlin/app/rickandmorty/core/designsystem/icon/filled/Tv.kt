package app.rickandmorty.core.designsystem.icon.filled

import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.icon.materialIcon
import app.rickandmorty.core.designsystem.icon.materialPath

@Suppress("UnusedReceiverParameter")
public val RamIcons.Filled.Tv: ImageVector
  get() {
    if (_tv != null) {
      return _tv!!
    }
    _tv =
      materialIcon(name = "Filled.Tv") {
        materialPath {
          moveTo(21.0f, 3.0f)
          lineTo(3.0f, 3.0f)
          curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
          verticalLineToRelative(12.0f)
          curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
          horizontalLineToRelative(5.0f)
          verticalLineToRelative(2.0f)
          horizontalLineToRelative(8.0f)
          verticalLineToRelative(-2.0f)
          horizontalLineToRelative(5.0f)
          curveToRelative(1.1f, 0.0f, 1.99f, -0.9f, 1.99f, -2.0f)
          lineTo(23.0f, 5.0f)
          curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
          close()
          moveTo(21.0f, 17.0f)
          lineTo(3.0f, 17.0f)
          lineTo(3.0f, 5.0f)
          horizontalLineToRelative(18.0f)
          verticalLineToRelative(12.0f)
          close()
        }
      }
    return _tv!!
  }

private var _tv: ImageVector? = null
