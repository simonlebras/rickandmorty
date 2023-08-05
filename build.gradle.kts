buildscript {
    dependencies {
        classpath(libs.play.osslicenses.plugin)
    }
}

plugins {
    id("app.rickandmorty.root")
    alias(libs.plugins.affectedmoduledetector)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.apollo) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.cachefix) apply false
    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.easylauncher) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.googleservices) apply false
    alias(libs.plugins.gradledoctor)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.modulegraphassert) apply false
    alias(libs.plugins.nodegradle)
    alias(libs.plugins.sortdependencies)
    alias(libs.plugins.spotless)
    alias(libs.plugins.wire) apply false
}
