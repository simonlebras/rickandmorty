package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.configureAndroid
import app.rickandmorty.gradle.utils.configureAndroidTesting
import app.rickandmorty.gradle.utils.configureKotlinAndroid
import app.rickandmorty.gradle.utils.configureSpotless
import com.android.build.gradle.TestExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class AndroidTestPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.android.test,
            libs.plugins.cacheFix,
            libs.plugins.kotlin.android,
            libs.plugins.sortDependencies,
        )

        configureKotlinAndroid(libs)

        configureSpotless(libs)

        configure<TestExtension> {
            configureAndroid(libs)

            configureAndroidTesting()

            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }
}
