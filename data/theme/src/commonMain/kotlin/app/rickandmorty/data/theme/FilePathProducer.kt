package app.rickandmorty.data.theme

import okio.Path

public fun interface FilePathProducer {
    public fun produceFilePath(fileName: String): Path
}
