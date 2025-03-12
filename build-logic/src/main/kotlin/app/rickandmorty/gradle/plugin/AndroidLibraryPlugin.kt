package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureAffectedAndroidTest
import app.rickandmorty.gradle.util.configureAndroid
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

public class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
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
            configureAndroid(this)

            val targetSdk = libs.versions.android.sdk.target.get().toInt()
            lint.targetSdk = targetSdk
            testOptions.targetSdk = targetSdk
        }
    }
}
