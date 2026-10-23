package app.rickandmorty.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

public fun viewModelCoroutineScope(): CoroutineScope =
  CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
