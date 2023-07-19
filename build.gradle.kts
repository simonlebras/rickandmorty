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
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.easylauncher) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.gradleDoctor)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.moduleGraphAssert) apply false
    alias(libs.plugins.nodeGradle)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.spotless)
    alias(libs.plugins.wire) apply false
}
