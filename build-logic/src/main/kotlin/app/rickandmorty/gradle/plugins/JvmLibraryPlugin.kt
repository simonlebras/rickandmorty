package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.configureJvmCiUnitTest
import app.rickandmorty.gradle.utils.configureKotlinJvm
import app.rickandmorty.gradle.utils.configureSpotless
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

public class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.kotlin.jvm,
            libs.plugins.sortDependencies,
        )

        configureKotlinJvm(libs)

        configureSpotless(libs)

        configureJvmCiUnitTest()
    }
}
