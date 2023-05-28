package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.configureAndroid
import app.rickandmorty.gradle.utils.configureAndroidCiUnitTest
import app.rickandmorty.gradle.utils.configureKotlinAndroid
import app.rickandmorty.gradle.utils.configureSpotless
import com.android.build.gradle.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.android.library,
            libs.plugins.cacheFix,
            libs.plugins.kotlin.android,
            libs.plugins.sortDependencies,
        )

        configureKotlinAndroid(libs)

        configureSpotless(libs)

        configure<LibraryExtension> {
            configureAndroid(libs)

            configureAndroidCiUnitTest()

            buildTypes {
                named("release") {
                    consumerProguardFiles("consumer-rules.pro")
                }
            }
        }
    }
}
