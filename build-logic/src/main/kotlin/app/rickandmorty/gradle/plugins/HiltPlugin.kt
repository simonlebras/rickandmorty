package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.api
import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.ksp
import app.rickandmorty.gradle.utils.toIdentifier
import app.rickandmorty.gradle.utils.withPlugin
import com.autonomousapps.DependencyAnalysisSubExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(
                libs.plugins.hilt,
                libs.plugins.ksp,
            )

            withPlugin(libs.plugins.dependencyanalysis) {
                configure<DependencyAnalysisSubExtension> {
                    issues {
                        val hiltAndroid = libs.hilt.android.asProvider().get().toIdentifier()!!
                        onUnusedDependencies {
                            exclude(hiltAndroid)
                        }
                        onIncorrectConfiguration {
                            exclude(hiltAndroid)
                        }
                    }
                }
            }
        }

        dependencies {
            api(libs.hilt.android)
            ksp(libs.hilt.android.compiler)
        }
    }
}
