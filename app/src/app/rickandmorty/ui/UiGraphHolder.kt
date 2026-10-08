package app.rickandmorty.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import app.rickandmorty.inject.UiGraph

class UiGraphHolder(val graph: UiGraph, savedStateHandle: SavedStateHandle) : ViewModel() {
  init {
    graph.navigationState.attachTo(savedStateHandle)
  }
}
