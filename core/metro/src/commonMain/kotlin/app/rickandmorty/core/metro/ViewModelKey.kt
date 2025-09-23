package app.rickandmorty.core.metro

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@MapKey public annotation class ViewModelKey(val value: KClass<out ViewModel>)
