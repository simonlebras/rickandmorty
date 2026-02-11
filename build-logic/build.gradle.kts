import tapmoc.configureJavaCompatibility

plugins {
  `java-gradle-plugin`
  alias(libs.plugins.android.lint)
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.sam)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.tapmoc)
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xcontext-parameters")
    allWarningsAsErrors = true
  }

  explicitApi()
}

configureJavaCompatibility(17)

samWithReceiver { annotation(HasImplicitReceiver::class.qualifiedName!!) }

ktfmt { googleStyle() }

lint {
  warningsAsErrors = true
  disable += setOf("InternalGradleApiUsage", "NewerVersionAvailable")
}

tasks {
  validatePlugins {
    enableStricterValidation = true
    failOnWarning = true
  }
}

dependencies {
  compileOnly(libs.android.tools.common)
  compileOnly(libs.android.tools.gradleapi)
  compileOnly(plugin(libs.plugins.compose.compiler))
  compileOnly(plugin(libs.plugins.dependencyanalysis))
  compileOnly(plugin(libs.plugins.kotlin.multiplatform))
  compileOnly(plugin(libs.plugins.ktfmt))
  compileOnly(plugin(libs.plugins.tapmoc))

  implementation(libs.truth)

  // Workaround for https://github.com/gradle/gradle/issues/15383
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

  lintChecks(libs.androidx.gradle.lints)
}

gradlePlugin {
  plugins {
    register("app.rickandmorty.android-application") {
      implementationClass = "app.rickandmorty.gradle.plugin.AndroidApplicationPlugin"
    }
    register("app.rickandmorty.android-library") {
      implementationClass = "app.rickandmorty.gradle.plugin.AndroidLibraryPlugin"
    }
    register("app.rickandmorty.android-multiplatformlibrary") {
      implementationClass = "app.rickandmorty.gradle.plugin.AndroidMultiplatformLibraryPlugin"
    }
    register("app.rickandmorty.android-test") {
      implementationClass = "app.rickandmorty.gradle.plugin.AndroidTestPlugin"
    }
    register("app.rickandmorty.codehealth") {
      implementationClass = "app.rickandmorty.gradle.plugin.CodeHealthPlugin"
    }
    register("app.rickandmorty.compose") {
      implementationClass = "app.rickandmorty.gradle.plugin.ComposePlugin"
    }
    register("app.rickandmorty.firebase-crashlytics") {
      implementationClass = "app.rickandmorty.gradle.plugin.FirebaseCrashlyticsPlugin"
    }
    register("app.rickandmorty.firebase-perf") {
      implementationClass = "app.rickandmorty.gradle.plugin.FirebasePerfPlugin"
    }
    register("app.rickandmorty.jvm-library") {
      implementationClass = "app.rickandmorty.gradle.plugin.JvmLibraryPlugin"
    }
    register("app.rickandmorty.kotlin-multiplatform") {
      implementationClass = "app.rickandmorty.gradle.plugin.KotlinMultiplatformPlugin"
    }
    register("app.rickandmorty.navigation-serialization") {
      implementationClass = "app.rickandmorty.gradle.plugin.NavigationSerializationPlugin"
    }
    register("app.rickandmorty.root") {
      implementationClass = "app.rickandmorty.gradle.plugin.RootPlugin"
    }
  }
}

private fun plugin(plugin: Provider<PluginDependency>) =
  plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version.requiredVersion}" }
