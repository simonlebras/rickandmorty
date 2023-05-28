package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.configureAndroidTesting
import app.rickandmorty.gradle.utils.withPlugins
import com.android.build.gradle.TestedExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

internal class AndroidInstrumentationTestingPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.withPlugins(
            libs.plugins.android.application,
            libs.plugins.android.library,
        ) {
            configure<TestedExtension> {
                configureAndroidTesting()

                defaultConfig {
                    testInstrumentationRunner = "app.rickandmorty.hilt.test.HiltTestRunner"
                }
            }
        }
    }
}
