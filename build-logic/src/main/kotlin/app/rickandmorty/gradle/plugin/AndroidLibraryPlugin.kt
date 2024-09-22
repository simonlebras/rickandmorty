package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureAffectedAndroidTest
import app.rickandmorty.gradle.util.configureAndroid
import com.android.build.api.variant.LibraryAndroidComponentsExtension
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
            libs.plugins.cachefix,
        )

        configureAffectedAndroidTest()

        configure<LibraryAndroidComponentsExtension> {
            beforeVariants(selector().withBuildType("debug")) { builder ->
                builder.enable = false
            }
        }

        configure<LibraryExtension> {
            configureAndroid(
                baseExtension = this,
                libs = libs,
            )
        }
    }
}
