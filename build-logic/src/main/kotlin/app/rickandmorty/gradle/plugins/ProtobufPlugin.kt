package app.rickandmorty.gradle.plugins

import app.rickandmorty.gradle.utils.apply
import app.rickandmorty.gradle.utils.implementation
import com.google.protobuf.gradle.ProtobufExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class ProtobufPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(libs.plugins.protobuf)

        configure<ProtobufExtension> {
            protoc {
                artifact = libs.protobuf.protoc.get().toString()
            }

            generateProtoTasks {
                all().forEach { task ->
                    task.builtins {
                        register("java") {
                            option("lite")
                        }
                        register("kotlin") {
                            option("lite")
                        }
                    }
                }
            }
        }

        dependencies {
            implementation(libs.protobuf.kotlin.lite)
        }
    }
}
