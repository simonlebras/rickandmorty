package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import com.jraska.module.graph.assertion.GraphRulesExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ModuleGraphAssertPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.modulegraphassert)

      configure<GraphRulesExtension> {
        maxHeight = 5
        allowed =
          arrayOf(
            ":app -> :ui:.*",
            ":app -> :data:.*",
            ":ui:.* -> :ui:[a-zA-Z0-9:\\-]*\\-common",
            ":ui:.* -> :data:.*",
            ":data:.* -> :data:.*",
            ":core:.* -> :data:model",
            ":.* -> :core:.*",
            ":.* -> :thirdparty:.*",
          )
        configurations +=
          setOf(
            "androidMainApi",
            "androidMainImplementation",
            "commonMainApi",
            "commonMainImplementation",
            "desktopMainApi",
            "desktopMainImplementation",
            "jsMainApi",
            "jsMainImplementation",
            "nativeMainApi",
            "nativeMainImplementation",
            "wasmJsMainApi",
            "wasmJsMainImplementation",
          )
      }
    }
}
