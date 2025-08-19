import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  `kotlin-dsl`
  alias(libs.plugins.android.lint)
  alias(libs.plugins.spotless)
}

val javaTarget = libs.versions.java.target.get()

kotlin {
  compilerOptions {
    jvmTarget = JvmTarget.fromTarget(javaTarget)

    allWarningsAsErrors = true

    freeCompilerArgs.addAll("-Xcontext-parameters")
  }

  explicitApi()
}

tasks.withType<JavaCompile>().configureEach {
  sourceCompatibility = javaTarget
  targetCompatibility = javaTarget
}

tasks {
  validatePlugins {
    enableStricterValidation = true
    failOnWarning = true
  }
}

lint {
  warningsAsErrors = true
  disable += setOf("InternalGradleApiUsage", "NewerVersionAvailable")
}

dependencies {
  compileOnly(libs.android.tools.common)
  compileOnly(plugin(libs.plugins.android.application))
  compileOnly(plugin(libs.plugins.compose.compiler))
  compileOnly(plugin(libs.plugins.dependencyanalysis))
  compileOnly(plugin(libs.plugins.gradledoctor))
  compileOnly(plugin(libs.plugins.kotlin.multiplatform))
  compileOnly(plugin(libs.plugins.modulegraphassert))
  compileOnly(plugin(libs.plugins.spotless))

  implementation(libs.truth)

  lintChecks(libs.androidx.gradle.lints)
}

gradlePlugin {
  plugins {
    register("android-application") {
      id = "app.rickandmorty.android-application"
      implementationClass = "app.rickandmorty.gradle.plugin.AndroidApplicationPlugin"
    }
    register("android-library") {
      id = "app.rickandmorty.android-library"
      implementationClass = "app.rickandmorty.gradle.plugin.AndroidLibraryPlugin"
    }
    register("android-multiplatformlibrary") {
      id = "app.rickandmorty.android-multiplatformlibrary"
      implementationClass = "app.rickandmorty.gradle.plugin.AndroidMultiplatformLibraryPlugin"
    }
    register("android-test") {
      id = "app.rickandmorty.android-test"
      implementationClass = "app.rickandmorty.gradle.plugin.AndroidTestPlugin"
    }
    register("compose") {
      id = "app.rickandmorty.compose"
      implementationClass = "app.rickandmorty.gradle.plugin.ComposePlugin"
    }
    register("firebase-crashlytics") {
      id = "app.rickandmorty.firebase-crashlytics"
      implementationClass = "app.rickandmorty.gradle.plugin.FirebaseCrashlyticsPlugin"
    }
    register("firebase-perf") {
      id = "app.rickandmorty.firebase-perf"
      implementationClass = "app.rickandmorty.gradle.plugin.FirebasePerfPlugin"
    }
    register("kotlin-android") {
      id = "app.rickandmorty.kotlin-android"
      implementationClass = "app.rickandmorty.gradle.plugin.KotlinAndroidPlugin"
    }
    register("kotlin-multiplatform") {
      id = "app.rickandmorty.kotlin-multiplatform"
      implementationClass = "app.rickandmorty.gradle.plugin.KotlinMultiplatformPlugin"
    }
    register("modulegraphassert") {
      id = "app.rickandmorty.modulegraphassert"
      implementationClass = "app.rickandmorty.gradle.plugin.ModuleGraphAssertPlugin"
    }
    register("root") {
      id = "app.rickandmorty.root"
      implementationClass = "app.rickandmorty.gradle.plugin.RootPlugin"
    }
    register("spotless") {
      id = "app.rickandmorty.spotless"
      implementationClass = "app.rickandmorty.gradle.plugin.SpotlessPlugin"
    }
  }
}

spotless {
  val ktfmtVersion = libs.versions.ktfmt.get()

  kotlin {
    ktfmt(ktfmtVersion).googleStyle().configure { it.setRemoveUnusedImports(true) }
    target("src/**/*.kt")
  }

  kotlinGradle {
    ktfmt(ktfmtVersion).googleStyle().configure { it.setRemoveUnusedImports(true) }
    target("*.kts")
  }
}

private fun plugin(plugin: Provider<PluginDependency>) =
  plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version.requiredVersion}" }
