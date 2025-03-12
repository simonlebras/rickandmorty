package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.configureAffectedAndroidTest
import app.rickandmorty.gradle.util.configureAndroid
import app.rickandmorty.gradle.util.configureBadgingTasks
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.AppExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.the

public class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.android.application,
            libs.plugins.cachefix,
        )

        configureAffectedAndroidTest()

        configure<ApplicationExtension> {
            configureAndroid(
                commonExtension = this,
                libs = libs,
            )

            defaultConfig.targetSdk = libs.versions.android.sdk.target.get().toInt()

            packaging.resources.excludes += setOf(
                "**/*.pro",
                "**/*.proto",
                "**/*.properties",
                "*.properties",
                "LICENSE_*",
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/CHANGES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
            )
        }

        configureBadgingTasks(
            appExtension = extensions.getByType<AppExtension>(),
            componentsExtension = extensions.getByType<ApplicationAndroidComponentsExtension>(),
        )

        configure<ApplicationAndroidComponentsExtension> {
            onVariants(selector().withBuildType("release")) { variant ->
                variant.packaging.resources.excludes.addAll(
                    "**/*.kotlin_metadata",
                    "DebugProbesKt.bin",
                    "kotlin/**",
                    "META-INF/*.kotlin_module",
                    "META-INF/*.version",
                    "META-INF/androidx.*",
                )
            }
        }
    }
}
