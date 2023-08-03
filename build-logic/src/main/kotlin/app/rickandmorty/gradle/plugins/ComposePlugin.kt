package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.androidTestImplementation
import app.rickandmorty.gradle.utils.implementation
import app.rickandmorty.gradle.utils.lintChecks
import app.rickandmorty.gradle.utils.withPlugins
import com.android.build.gradle.BaseExtension
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

        pluginManager.withPlugins(
            libs.plugins.android.application,
            libs.plugins.android.library,
        ) {
            configureCompose(libs)
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

        lintChecks(libs.slack.compose.lints)
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    return buildList {
        val enableMetricsProvider = providers.gradleProperty("enableComposeCompilerMetrics")
        val enableMetrics = enableMetricsProvider.orNull == "true"
        if (enableMetrics) {
            val metricsFolder = layout.buildDirectory.dir("compose-metrics").get()
            add("-P")
            add(
                "plugin" +
                    ":androidx.compose.compiler.plugins.kotlin" +
                    ":metricsDestination=$metricsFolder",
            )
        }

        val enableReportsProvider = providers.gradleProperty("enableComposeCompilerReports")
        val enableReports = enableReportsProvider.orNull == "true"
        if (enableReports) {
            val reportsFolder = layout.buildDirectory.dir("compose-reports").get()
            add("-P")
            add(
                "plugin" +
                    ":androidx.compose.compiler.plugins.kotlin" +
                    ":reportsDestination=$reportsFolder",
            )
        }
    }
}
