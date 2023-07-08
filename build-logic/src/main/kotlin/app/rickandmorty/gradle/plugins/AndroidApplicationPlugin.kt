package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.configureAndroid
import app.rickandmorty.gradle.utils.configureKotlinAndroid
import app.rickandmorty.gradle.utils.configureSpotless
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
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
            libs.plugins.cacheFix,
            libs.plugins.kotlin.android,
            libs.plugins.sortDependencies,
        )

        configureKotlinAndroid(libs)

        configureSpotless(libs)

        configure<BaseAppModuleExtension> {
            configureAndroid(libs)

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }

            buildTypes {
                named("release") {
                    isShrinkResources = true
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro",
                    )
                }
            }
        }
    }
}
