package app.rickandmorty.data.filesystem.inject

import me.tatarka.inject.annotations.Provides
import okio.FileSystem
import okio.SYSTEM
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
public interface FileSystemComponent {
    @Provides
    public fun provideFileSystem(): FileSystem = FileSystem.SYSTEM
}
