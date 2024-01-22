package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.androidTestImplementation
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.lintChecks
import app.rickandmorty.gradle.util.withPlugins
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
            kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs += buildComposeCompilerArgs()
        }
    }

    dependencies {
        implementation(platform(libs.androidx.compose.bom))

        androidTestImplementation(platform(libs.androidx.compose.bom))

        lintChecks(libs.slack.compose.lints)
    }
}

private fun Project.buildComposeCompilerArgs(): List<String> {
    return buildList {
        val enableMetrics = providers.gradleProperty("enableComposeCompilerMetrics").orNull == "true"
        if (enableMetrics) {
            val metricsFolder = layout.buildDirectory.dir("compose-metrics").get()
            add("-P")
            add(
                "plugin" +
                    ":androidx.compose.compiler.plugins.kotlin" +
                    ":metricsDestination=$metricsFolder",
            )
        }

        val enableReports = providers.gradleProperty("enableComposeCompilerReports").orNull == "true"
        if (enableReports) {
            val reportsFolder = layout.buildDirectory.dir("compose-reports").get()
            add("-P")
            add(
                "plugin" +
                    ":androidx.compose.compiler.plugins.kotlin" +
                    ":reportsDestination=$reportsFolder",
            )
        }

        // Enable strong skipping mode https://android-review.googlesource.com/c/platform/frameworks/support/+/2671135
        add("-P")
        add(
            "plugin" +
                ":androidx.compose.compiler.plugins.kotlin" +
                ":experimentalStrongSkipping=true",
        )
    }
}
