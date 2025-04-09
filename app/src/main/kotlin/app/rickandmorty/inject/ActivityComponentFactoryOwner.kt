package app.rickandmorty.inject

fun interface ActivityComponentFactoryOwner {
  fun activityComponentFactory(): ActivityComponent.Factory
}
