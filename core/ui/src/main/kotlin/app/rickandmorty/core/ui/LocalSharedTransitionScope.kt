package app.rickandmorty.core.ui

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

@SuppressLint("ComposeCompositionLocalUsage")
@OptIn(ExperimentalSharedTransitionApi::class)
public val LocalSharedTransitionScope: ProvidableCompositionLocal<SharedTransitionScope> =
    staticCompositionLocalOf { error("SharedTransitionScope not provided") }
