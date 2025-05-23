package app.rickandmorty.core.designsystem.icon.filled

import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.icon.materialIcon
import app.rickandmorty.core.designsystem.icon.materialPath

@Suppress("UnusedReceiverParameter")
public val RamIcons.Filled.Face: ImageVector
  get() {
    if (_face != null) {
      return _face!!
    }
    _face =
      materialIcon(name = "Filled.Face") {
        materialPath {
          moveTo(9.0f, 11.75f)
          curveToRelative(-0.69f, 0.0f, -1.25f, 0.56f, -1.25f, 1.25f)
          reflectiveCurveToRelative(0.56f, 1.25f, 1.25f, 1.25f)
          reflectiveCurveToRelative(1.25f, -0.56f, 1.25f, -1.25f)
          reflectiveCurveToRelative(-0.56f, -1.25f, -1.25f, -1.25f)
          close()
          moveTo(15.0f, 11.75f)
          curveToRelative(-0.69f, 0.0f, -1.25f, 0.56f, -1.25f, 1.25f)
          reflectiveCurveToRelative(0.56f, 1.25f, 1.25f, 1.25f)
          reflectiveCurveToRelative(1.25f, -0.56f, 1.25f, -1.25f)
          reflectiveCurveToRelative(-0.56f, -1.25f, -1.25f, -1.25f)
          close()
          moveTo(12.0f, 2.0f)
          curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
          reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
          reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
          reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
          close()
          moveTo(12.0f, 20.0f)
          curveToRelative(-4.41f, 0.0f, -8.0f, -3.59f, -8.0f, -8.0f)
          curveToRelative(0.0f, -0.29f, 0.02f, -0.58f, 0.05f, -0.86f)
          curveToRelative(2.36f, -1.05f, 4.23f, -2.98f, 5.21f, -5.37f)
          curveTo(11.07f, 8.33f, 14.05f, 10.0f, 17.42f, 10.0f)
          curveToRelative(0.78f, 0.0f, 1.53f, -0.09f, 2.25f, -0.26f)
          curveToRelative(0.21f, 0.71f, 0.33f, 1.47f, 0.33f, 2.26f)
          curveToRelative(0.0f, 4.41f, -3.59f, 8.0f, -8.0f, 8.0f)
          close()
        }
      }
    return _face!!
  }

private var _face: ImageVector? = null
