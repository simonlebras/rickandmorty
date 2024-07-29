package app.rickandmorty.data.license

import android.content.Context
import app.rickandmorty.core.coroutines.IODispatcher
import app.rickandmorty.data.model.License
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import se.ansman.dagger.auto.AutoBind

@AutoBind
internal class LicenseRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : LicenseRepository {
    override suspend fun getLicense(): ImmutableList<License> {
        return withContext(ioDispatcher) {
            val json = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
            json.decodeFromStream<List<LicenseItem>>(context.assets.open("artifacts.json"))
                .map {
                    License(
                        it.groupId,
                        it.artifactId,
                        it.version,
                    )
                }
                .toPersistentList()
        }
    }
}
