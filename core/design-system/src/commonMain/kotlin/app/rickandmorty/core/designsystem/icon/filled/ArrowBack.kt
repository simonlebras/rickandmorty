package app.rickandmorty.core.designsystem.icon.filled

import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.icon.materialIcon
import app.rickandmorty.core.designsystem.icon.materialPath

@Suppress("UnusedReceiverParameter")
public val RamIcons.Filled.ArrowBack: ImageVector
  get() {
    if (_arrowBack != null) {
      return _arrowBack!!
    }
    _arrowBack =
      materialIcon(name = "Filled.ArrowBack", autoMirror = true) {
        materialPath {
          moveTo(20.0f, 11.0f)
          horizontalLineTo(7.83f)
          lineToRelative(5.59f, -5.59f)
          lineTo(12.0f, 4.0f)
          lineToRelative(-8.0f, 8.0f)
          lineToRelative(8.0f, 8.0f)
          lineToRelative(1.41f, -1.41f)
          lineTo(7.83f, 13.0f)
          horizontalLineTo(20.0f)
          verticalLineToRelative(-2.0f)
          close()
        }
      }
    return _arrowBack!!
  }

private var _arrowBack: ImageVector? = null
