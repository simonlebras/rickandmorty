package app.rickandmorty.core.navigation.serialization

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS) public annotation class NavKeySerializer(val scope: KClass<*>)
