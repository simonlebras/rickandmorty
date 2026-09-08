package app.rickandmorty.core.filesystem.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import okio.FileSystem
import okio.SYSTEM

@BindingContainer
@ContributesTo(AppScope::class)
public object FileSystemProvider {
  @Provides public fun provideFileSystem(): FileSystem = FileSystem.SYSTEM
}
