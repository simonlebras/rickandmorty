package app.rickandmorty.core.filesystem.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import okio.FileSystem
import okio.SYSTEM

@ContributesTo(AppScope::class)
public interface FileSystemProvider {
  public companion object {
    @Provides public fun provideFileSystem(): FileSystem = FileSystem.SYSTEM
  }
}
