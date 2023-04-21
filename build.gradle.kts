buildscript {
    dependencies {
        classpath(libs.play.ossLicenses.gradlePlugin)
    }
}

plugins {
    id("app.rickandmorty.root")
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.apollo) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.cacheFix) apply false
    alias(libs.plugins.dependencyAnalysis) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.gradleDoctor) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.nodeGradle) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.sortDependencies) apply false
    alias(libs.plugins.spotless) apply false
}
