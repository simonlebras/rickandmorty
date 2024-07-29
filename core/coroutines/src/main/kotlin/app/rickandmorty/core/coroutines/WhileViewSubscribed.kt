package app.rickandmorty.core.coroutines

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed

public val WhileViewSubscribed: SharingStarted = SharingStarted.WhileSubscribed(5.seconds)
