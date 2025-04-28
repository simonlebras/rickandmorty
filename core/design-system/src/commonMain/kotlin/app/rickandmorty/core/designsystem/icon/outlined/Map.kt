package app.rickandmorty.core.designsystem.icon.outlined

import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.icon.materialIcon
import app.rickandmorty.core.designsystem.icon.materialPath

@Suppress("UnusedReceiverParameter")
public val RamIcons.Outlined.Map: ImageVector
  get() {
    if (_map != null) {
      return _map!!
    }
    _map =
      materialIcon(name = "Outlined.Map") {
        materialPath {
          moveTo(20.5f, 3.0f)
          lineToRelative(-0.16f, 0.03f)
          lineTo(15.0f, 5.1f)
          lineTo(9.0f, 3.0f)
          lineTo(3.36f, 4.9f)
          curveToRelative(-0.21f, 0.07f, -0.36f, 0.25f, -0.36f, 0.48f)
          lineTo(3.0f, 20.5f)
          curveToRelative(0.0f, 0.28f, 0.22f, 0.5f, 0.5f, 0.5f)
          lineToRelative(0.16f, -0.03f)
          lineTo(9.0f, 18.9f)
          lineToRelative(6.0f, 2.1f)
          lineToRelative(5.64f, -1.9f)
          curveToRelative(0.21f, -0.07f, 0.36f, -0.25f, 0.36f, -0.48f)
          lineTo(21.0f, 3.5f)
          curveToRelative(0.0f, -0.28f, -0.22f, -0.5f, -0.5f, -0.5f)
          close()
          moveTo(10.0f, 5.47f)
          lineToRelative(4.0f, 1.4f)
          verticalLineToRelative(11.66f)
          lineToRelative(-4.0f, -1.4f)
          lineTo(10.0f, 5.47f)
          close()
          moveTo(5.0f, 6.46f)
          lineToRelative(3.0f, -1.01f)
          verticalLineToRelative(11.7f)
          lineToRelative(-3.0f, 1.16f)
          lineTo(5.0f, 6.46f)
          close()
          moveTo(19.0f, 17.54f)
          lineToRelative(-3.0f, 1.01f)
          lineTo(16.0f, 6.86f)
          lineToRelative(3.0f, -1.16f)
          verticalLineToRelative(11.84f)
          close()
        }
      }
    return _map!!
  }

private var _map: ImageVector? = null
