package app.rickandmorty.core.metro

import android.app.Activity
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@MapKey public annotation class ActivityKey(val value: KClass<out Activity>)
