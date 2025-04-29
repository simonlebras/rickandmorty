package app.rickandmorty.core.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

// All Material icons (currently) are 24dp by 24dp, with a viewport size of 24 by 24.
private const val MaterialIconDimension = 24f

internal inline fun materialIcon(
  name: String,
  block: ImageVector.Builder.() -> ImageVector.Builder,
): ImageVector =
  ImageVector.Builder(
      name = name,
      defaultWidth = MaterialIconDimension.dp,
      defaultHeight = MaterialIconDimension.dp,
      viewportWidth = MaterialIconDimension,
      viewportHeight = MaterialIconDimension,
    )
    .block()
    .build()

internal inline fun materialIcon(
  name: String,
  autoMirror: Boolean = false,
  block: ImageVector.Builder.() -> ImageVector.Builder,
): ImageVector =
  ImageVector.Builder(
      name = name,
      defaultWidth = MaterialIconDimension.dp,
      defaultHeight = MaterialIconDimension.dp,
      viewportWidth = MaterialIconDimension,
      viewportHeight = MaterialIconDimension,
      autoMirror = autoMirror,
    )
    .block()
    .build()

internal inline fun ImageVector.Builder.materialPath(
  fillAlpha: Float = 1f,
  strokeAlpha: Float = 1f,
  pathFillType: PathFillType = DefaultFillType,
  pathBuilder: PathBuilder.() -> Unit,
) =
  path(
    fill = SolidColor(Color.Black),
    fillAlpha = fillAlpha,
    stroke = null,
    strokeAlpha = strokeAlpha,
    strokeLineWidth = 1f,
    strokeLineCap = StrokeCap.Butt,
    strokeLineJoin = StrokeJoin.Bevel,
    strokeLineMiter = 1f,
    pathFillType = pathFillType,
    pathBuilder = pathBuilder,
  )
