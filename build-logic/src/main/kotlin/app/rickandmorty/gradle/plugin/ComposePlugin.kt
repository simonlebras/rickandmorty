package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.androidTestImplementation
import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.withPlugins
import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

public class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.kotlin.compose.compiler)

            withPlugins(
                libs.plugins.android.application,
                libs.plugins.android.library,
            ) {
                configure<BaseExtension> {
                    buildFeatures.compose = true
                }
            }
        }

        configure<ComposeCompilerGradlePluginExtension> {
            featureFlags = setOf(
                ComposeFeatureFlag.OptimizeNonSkippingGroups,
            )

            val enableComposeCompilerReports = providers
                .gradleProperty("ram.enableComposeCompilerReports")
                .orNull == "true"
            if (enableComposeCompilerReports) {
                val composeReportsFolder = layout.buildDirectory.map { buildDir ->
                    buildDir.dir("reports").dir("compose")
                }
                reportsDestination = composeReportsFolder
                metricsDestination = composeReportsFolder
            }
        }

        dependencies {
            implementation(platform(libs.androidx.compose.bom))

            androidTestImplementation(platform(libs.androidx.compose.bom))
        }
    }
}
