package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureAffectedAndroidTest
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureBadgingTasks
import com.android.build.gradle.AppExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.android.application,
            libs.plugins.cachefix,
        )

        configureAffectedAndroidTest()

        configure<AppExtension> {
            configureAndroid(
                baseExtension = this,
                libs = libs,
            )

            configureBadgingTasks(this)

            packagingOptions {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }
    }
}
