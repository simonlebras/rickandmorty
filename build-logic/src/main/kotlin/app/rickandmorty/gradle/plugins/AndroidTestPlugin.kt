package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.configureAndroid
import app.rickandmorty.gradle.utils.configureKotlin
import com.android.build.gradle.TestExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class AndroidTestPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply("app.rickandmorty.spotless")
            apply(libs.plugins.android.test.get().pluginId)
            apply(libs.plugins.cacheFix.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.sortDependencies.get().pluginId)
        }

        configureKotlin(
            libs = libs,
            explicitApi = false,
        )

        configure<TestExtension> {
            configureAndroid(libs)
        }
    }
}
