package app.rickandmorty.core.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.Composable

public const val ShowNavSuiteKey: String = "app.rickandmorty.adaptive.layout.ShowNavSuite"

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
public fun listPaneWithNavSuite(
  sceneKey: Any = Unit,
  detailPlaceholder: @Composable ThreePaneScaffoldScope.() -> Unit = {},
): Map<String, Any> {
  return ListDetailSceneStrategy.listPane(
    sceneKey = sceneKey,
    detailPlaceholder = detailPlaceholder,
  ) + (ShowNavSuiteKey to true)
}
