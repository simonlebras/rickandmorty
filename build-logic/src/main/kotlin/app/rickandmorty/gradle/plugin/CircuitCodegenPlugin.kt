package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.kspDependencies
import app.rickandmorty.gradle.util.withPlugin
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class CircuitCodegenPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        with(pluginManager) {
            apply(libs.plugins.ksp)

            withPlugin(libs.plugins.kotlin.multiplatform) {
                configure<KotlinMultiplatformExtension> {
                    sourceSets.commonMain.dependencies {
                        api(libs.circuit.codegen.annotations)
                    }

                    kspDependencies {
                        ksp(libs.circuit.codegen)
                    }
                }
            }
        }

        configure<KspExtension> {
            arg("circuit.codegen.mode", "kotlin_inject_anvil")
            arg(
                "kotlin-inject-anvil-contributing-annotations",
                "com.slack.circuit.codegen.annotations.CircuitInject",
            )
        }
    }
}
