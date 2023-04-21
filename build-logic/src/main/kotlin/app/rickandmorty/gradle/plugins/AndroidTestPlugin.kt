package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureKotlin
import com.android.build.gradle.TestExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

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
            explicitApi = ExplicitApiMode.Disabled,
        )

        configure<TestExtension> {
            configureAndroid(libs)
        }
    }
}
