package app.rickandmorty.core.ui

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType

public val NavigationSuiteType.isNavigationBar: Boolean
  get() =
    this == NavigationSuiteType.ShortNavigationBarCompact ||
      this == NavigationSuiteType.ShortNavigationBarMedium ||
      this == NavigationSuiteType.NavigationBar

public val NavigationSuiteType.isNavigationRail: Boolean
  get() =
    this == NavigationSuiteType.WideNavigationRailCollapsed ||
      this == NavigationSuiteType.WideNavigationRailExpanded ||
      this == NavigationSuiteType.NavigationRail
