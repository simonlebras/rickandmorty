package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureKotlin
import com.android.build.gradle.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.android.library.get().pluginId)
            apply(libs.plugins.kotlin.android.get().pluginId)
            apply(libs.plugins.cacheFix.get().pluginId)
            apply(libs.plugins.sortDependencies.get().pluginId)
            apply("app.rickandmorty.spotless")
        }

        configureKotlin(libs)

        configure<LibraryExtension> {
            configureAndroid(libs)

            buildTypes {
                named("release") {
                    consumerProguardFiles("consumer-rules.pro")
                }
            }
        }
    }
}
