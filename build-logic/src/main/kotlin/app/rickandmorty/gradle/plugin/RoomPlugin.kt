package app.rickandmorty.gradle.plugin

import androidx.room.gradle.RoomExtension
import app.rickandmorty.gradle.util.api
import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.implementation
import app.rickandmorty.gradle.util.ksp
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class RoomPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(
            libs.plugins.androidx.room,
            libs.plugins.ksp,
        )

        configure<KspExtension> {
            arg("room.generateKotlin", "true")
        }

        configure<RoomExtension> {
            schemaDirectory("$projectDir/schemas")
        }

        dependencies {
            api(libs.androidx.room.runtime)
            implementation(libs.androidx.room)
            ksp(libs.androidx.room.compiler)
        }
    }
}
