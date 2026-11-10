package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.configureKotlin
import app.rickandmorty.gradle.util.configureLint
import app.rickandmorty.gradle.util.kotlinMultiplatform
import com.android.build.api.dsl.Lint
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class KotlinMultiplatformPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.android.lint, libs.plugins.kotlin.multiplatform)

      configureKotlin()

      configure<Lint> { configureLint() }

      kotlinMultiplatform {
        applyDefaultHierarchyTemplate()

        iosArm64()
        iosSimulatorArm64()

        jvm()

        explicitApi()
      }
    }
}
