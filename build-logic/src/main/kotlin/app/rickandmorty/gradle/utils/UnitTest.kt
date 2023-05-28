package app.rickandmorty.gradle.utils

import com.android.build.gradle.TestedExtension
import java.util.Locale
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.language.base.plugins.LifecycleBasePlugin

private const val GLOBAL_CI_UNIT_TEST_TASK_NAME = "globalCiUnitTest"
private const val CI_UNIT_TEST_TASK_NAME = "ciUnitTest"
private const val COMPILE_CI_UNIT_TEST_NAME = "compileCiUnitTest"
private val CI_TEST_VARIANT = "debug".capitalizeUS()

private val Project.globalCiUnitTestTask: TaskProvider<Task>
    get() = rootProject.tasks.named(GLOBAL_CI_UNIT_TEST_TASK_NAME)

internal fun Project.configureRootCiUnitTest(): TaskProvider<Task> {
    require(isRootProject) {
        "Root CI unit test configuration should only be applied on the root project."
    }

    return project.tasks.register(GLOBAL_CI_UNIT_TEST_TASK_NAME) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Global lifecycle task to run all ciUnitTest tasks."
    }
}

context (Project)
internal fun TestedExtension.configureAndroidCiUnitTest() {
    val variantUnitTestTaskName = "test${CI_TEST_VARIANT}UnitTest"
    val ciUnitTest = tasks.register(CI_UNIT_TEST_TASK_NAME) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        dependsOn(variantUnitTestTaskName)
    }
    globalCiUnitTestTask.configure { dependsOn(ciUnitTest) }

    val variantCompileUnitTestTaskName = "compile${CI_TEST_VARIANT}UnitTestSources"
    tasks.register(COMPILE_CI_UNIT_TEST_NAME) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        dependsOn(variantCompileUnitTestTaskName)
    }
}

internal fun Project.configureJvmCiUnitTest() {
    val ciUnitTest = tasks.register(CI_UNIT_TEST_TASK_NAME) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        dependsOn("test")
    }
    globalCiUnitTestTask.configure { dependsOn(ciUnitTest) }

    tasks.register(COMPILE_CI_UNIT_TEST_NAME) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        dependsOn("testClasses")
    }
}

private fun String.capitalizeUS(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.US) else it.toString() }
}
