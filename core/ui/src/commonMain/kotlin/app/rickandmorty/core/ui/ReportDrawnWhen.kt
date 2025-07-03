package app.rickandmorty.core.ui

import androidx.compose.runtime.Composable

@Composable public expect fun ReportDrawnWhen(predicate: () -> Boolean)
