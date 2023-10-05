package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureAffectedAndroidTest
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureKotlinAndroid
import app.rickandmorty.gradle.util.configureSpotless
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
            libs.plugins.cachefix,
            libs.plugins.kotlin.android,
            libs.plugins.sortdependencies,
        )

        configureKotlinAndroid(libs)

        configureSpotless(libs)

        configureAffectedAndroidTest()

        configure<TestExtension> {
            configureAndroid(libs)
        }
    }
}
