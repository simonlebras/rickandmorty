package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.api
import app.rickandmorty.gradle.util.apply
import com.squareup.wire.gradle.WireExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

public class WirePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = the<LibrariesForLibs>()

        pluginManager.apply(libs.plugins.wire)

        configure<WireExtension> {
            kotlin {}
        }

        dependencies {
            api(libs.wire.runtime)
        }
    }
}
