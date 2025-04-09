package app.rickandmorty.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.ui.graphics.vector.ImageVector

public object RamIcons {
  public object Filled {
    public val ArrowBack: ImageVector = Icons.AutoMirrored.Filled.ArrowBack
    public val Check: ImageVector = Icons.Filled.Check
    public val Face: ImageVector = Icons.Filled.Face
    public val Map: ImageVector = Icons.Filled.Map
    public val Settings: ImageVector = Icons.Filled.Settings
    public val Tv: ImageVector = Icons.Filled.Tv
  }

  public object Outlined {
    public val Face: ImageVector = Icons.Outlined.Face
    public val Map: ImageVector = Icons.Outlined.Map
    public val Tv: ImageVector = Icons.Outlined.Tv
  }
}
