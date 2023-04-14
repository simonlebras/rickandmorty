package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.util.androidTestImplementation
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.lintChecks
import com.android.build.gradle.BaseExtension
import java.io.File
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()
        with(pluginManager) {
            withPlugin(libs.plugins.android.application.get().pluginId) {
                configureCompose(libs)
            }

            withPlugin(libs.plugins.android.library.get().pluginId) {
                configureCompose(libs)
            }
        }
    }
}

private fun Project.configureCompose(libs: LibrariesForLibs) {
    configure<BaseExtension> {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += buildComposeMetricsParameters()
        }
    }

    dependencies {
        implementation(platform(libs.androidx.compose.bom))

        androidTestImplementation(platform(libs.androidx.compose.bom))

        lintChecks(libs.compose.lints)
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    return buildList {
        val enableMetricsProvider = providers.gradleProperty("enableComposeCompilerMetrics")
        val enableMetrics = enableMetricsProvider.orNull == "true"
        if (enableMetrics) {
            val metricsFolder = File(buildDir, "compose-metrics")
            add("-P")
            add(
                "plugin" +
                    ":androidx.compose.compiler.plugins.kotlin" +
                    ":metricsDestination=${metricsFolder.absolutePath}",
            )
        }

        val enableReportsProvider = providers.gradleProperty("enableComposeCompilerReports")
        val enableReports = enableReportsProvider.orNull == "true"
        if (enableReports) {
            val reportsFolder = File(buildDir, "compose-reports")
            add("-P")
            add(
                "plugin" +
                    ":androidx.compose.compiler.plugins.kotlin" +
                    ":reportsDestination=${reportsFolder.absolutePath}",
            )
        }
    }
}
