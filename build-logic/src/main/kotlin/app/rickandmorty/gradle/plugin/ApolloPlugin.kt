package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import com.apollographql.apollo.gradle.internal.ApolloGenerateSourcesFromIrTask
import com.autonomousapps.tasks.CodeSourceExploderTask
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType

public class ApolloPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(libs.plugins.apollo)

        // https://github.com/gradle/gradle/issues/25885
        tasks.withType<CodeSourceExploderTask>().configureEach {
            dependsOn(tasks.withType<ApolloGenerateSourcesFromIrTask>())
        }
    }
}
