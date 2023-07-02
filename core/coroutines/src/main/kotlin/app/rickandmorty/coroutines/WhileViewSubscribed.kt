package app.rickandmorty.coroutines

import kotlinx.coroutines.flow.SharingStarted

public val WhileViewSubscribed: SharingStarted = SharingStarted.WhileSubscribed(5_000)
